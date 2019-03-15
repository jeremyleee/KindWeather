package com.tragicfruit.weatherapp.controllers

import android.app.IntentService
import android.content.Intent
import com.google.android.gms.location.LocationResult
import com.tragicfruit.weatherapp.model.ForecastPeriod
import com.tragicfruit.weatherapp.model.WeatherAlert
import io.realm.Realm
import io.realm.Sort
import io.realm.kotlin.where
import java.util.*
import java.util.concurrent.TimeUnit

class AlertService : IntentService(AlertService::javaClass.name) {

    private val calendar = Calendar.getInstance().apply {
        timeInMillis = System.currentTimeMillis()
    }

    override fun onHandleIntent(intent: Intent?) {
        if (LocationResult.hasResult(intent)) {
            val location = LocationResult.extractResult(intent).lastLocation
            WeatherController.fetchForecast(location.latitude, location.longitude) { success, code, message ->
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
            .findFirst()

        todaysForecast?.let { forecast ->
            val enabledAlerts = realm.where<WeatherAlert>()
                .equalTo("enabled", true)
                .sort("priority", Sort.ASCENDING)
                .findAll()

            // Highest priority alert
            val showAlert = enabledAlerts.first { it.shouldShowAlert(forecast) }

            showAlert?.let { alert ->
                NotificationController.notifyWeatherAlert(this, alert, forecast)
            }
        }
    }

}