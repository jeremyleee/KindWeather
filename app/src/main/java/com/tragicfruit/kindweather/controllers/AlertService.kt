package com.tragicfruit.kindweather.controllers

import android.app.IntentService
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationResult
import com.tragicfruit.kindweather.R
import com.tragicfruit.kindweather.data.ForecastRepository
import com.tragicfruit.kindweather.data.NotificationRepository
import com.tragicfruit.kindweather.model.ForecastPeriod
import com.tragicfruit.kindweather.model.WeatherAlert
import com.tragicfruit.kindweather.utils.SharedPrefsHelper
import dagger.hilt.android.AndroidEntryPoint
import io.realm.Realm
import io.realm.Sort
import io.realm.kotlin.where
import timber.log.Timber
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class AlertService : IntentService(AlertService::javaClass.name) {

    @Inject lateinit var notificationRepository: NotificationRepository
    @Inject lateinit var forecastRepository: ForecastRepository
    @Inject lateinit var notificationController: NotificationController
    @Inject lateinit var sharedPrefsHelper: SharedPrefsHelper

    private val calendar = Calendar.getInstance().apply {
        timeInMillis = System.currentTimeMillis()
    }

    override fun onHandleIntent(intent: Intent?) {
        startForeground(FOREGROUND_ID, notificationController.getAlertForegroundNotification(this))

        if (LocationResult.hasResult(intent)) {
            val location = LocationResult.extractResult(intent).lastLocation
            forecastRepository.fetchForecast(location.latitude, location.longitude) { success, code, message ->
                if (!success) {
                    // TODO: logic for comparing cached forecasts with current location
                }

                displayWeatherAlert()
            }
        }
    }

    private fun displayWeatherAlert() {
        val realm = Realm.getDefaultInstance()

        val now = calendar.time
        calendar.add(Calendar.DAY_OF_MONTH, -1)
        val yesterday = calendar.time

        val todaysForecast = realm.where<ForecastPeriod>()
            .greaterThan("time", TimeUnit.MILLISECONDS.toSeconds(yesterday.time))
            .lessThanOrEqualTo("time", TimeUnit.MILLISECONDS.toSeconds(now.time))
            .equalTo("displayOnly", false)
            .findFirst()

        todaysForecast?.let { forecast ->
            val enabledAlerts = realm.where<WeatherAlert>()
                .equalTo("enabled", true)
                .sort("priority", Sort.ASCENDING)
                .findAll()

            // Highest priority alert
            val showAlert = enabledAlerts.firstOrNull {
                it.shouldShowAlert(forecast, sharedPrefsHelper.usesImperialUnits())
            }

            var message = getString(R.string.feed_entry_default)
            var color = ContextCompat.getColor(this, R.color.alert_no_notification)

            showAlert?.let { alert ->
                message = getString(alert.getInfo().title)
                color = ContextCompat.getColor(this, alert.getInfo().color)
                Timber.i("Showing push notification: $message")
                notificationController.notifyWeatherAlert(this, alert, forecast)
            }

            // Create model object for feed
            notificationRepository.createNotification(message, forecast, color)
            forecastRepository.setDisplayed(forecast, true)
        }
    }

    companion object {
        const val FOREGROUND_ID = 200

        fun getIntent(context: Context) = Intent(context, AlertService::class.java)
    }

}