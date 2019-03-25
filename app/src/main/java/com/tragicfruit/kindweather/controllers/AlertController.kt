package com.tragicfruit.kindweather.controllers

import android.Manifest
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.tragicfruit.kindweather.model.ForecastType
import com.tragicfruit.kindweather.model.WeatherAlert
import com.tragicfruit.kindweather.utils.PermissionHelper
import com.tragicfruit.kindweather.utils.SharedPrefsHelper
import io.realm.Realm
import io.realm.kotlin.where
import timber.log.Timber
import java.util.*

object AlertController {

    private const val ALERT_RECEIVER_REQUEST = 300

    private val calendar = Calendar.getInstance()

    fun createAlerts() {
        if (Realm.getDefaultInstance().where<WeatherAlert>().count() > 0) {
            // Alerts already created
            return
        }

        // TODO: replace with proper alerts
        Realm.getDefaultInstance().executeTransaction { realm ->
            WeatherAlert.create(1, WeatherAlert.Info.UMBRELLA, realm).also { alert ->
                WeatherAlert.addParam(alert, ForecastType.PRECIP_PROBABILITY, 0.5, null, realm)
                WeatherAlert.addParam(alert, ForecastType.WIND_GUST, null, 10.8, realm)
            }

            WeatherAlert.create(2, WeatherAlert.Info.RAIN_JACKET, realm).also { alert ->
                WeatherAlert.addParam(alert, ForecastType.PRECIP_PROBABILITY, 0.5, null, realm)
                WeatherAlert.addParam(alert, ForecastType.WIND_GUST, 10.8, null, realm)
            }

            WeatherAlert.create(3, WeatherAlert.Info.SUNSCREEN, realm).also { alert ->
                WeatherAlert.addParam(alert, ForecastType.UV_INDEX, 6.0, null, realm)
            }
        }
    }

    fun scheduleDailyAlert(context: Context) {
        val alarmManager = ContextCompat.getSystemService(context, AlarmManager::class.java)

        val intent = AlertReceiver.getIntent(context)
        val pendingIntent = PendingIntent.getBroadcast(context, ALERT_RECEIVER_REQUEST, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        calendar.timeInMillis = System.currentTimeMillis()
        calendar.set(Calendar.HOUR_OF_DAY, SharedPrefsHelper.getAlertHour())
        calendar.set(Calendar.MINUTE, SharedPrefsHelper.getAlertMinute())
        calendar.set(Calendar.SECOND, 0)

        if (calendar.time.before(Date())) {
            // If time is earlier today, schedule for tomorrow
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }

        Timber.i("Daily alert scheduled for ${calendar.time}")
        alarmManager?.setRepeating(AlarmManager.RTC, calendar.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent)
    }

}

class AlertReceiver : BroadcastReceiver() {

    @SuppressWarnings("MissingPermission")
    override fun onReceive(context: Context?, intent: Intent?) {
        val validContext = context ?: return

        if (PermissionHelper.hasPermission(validContext, Manifest.permission.ACCESS_FINE_LOCATION)) {
            Timber.d("Requesting current location")

            // Request current location
            val fusedLocationClient = LocationServices.getFusedLocationProviderClient(validContext)
            val locationRequest = LocationRequest.create().setNumUpdates(1)

            val serviceIntent = AlertService.getIntent(validContext)
            val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                PendingIntent.getForegroundService(validContext, ALERT_SERVICE_REQUEST, serviceIntent, PendingIntent.FLAG_UPDATE_CURRENT)
            } else {
                PendingIntent.getService(validContext, ALERT_SERVICE_REQUEST, serviceIntent, PendingIntent.FLAG_UPDATE_CURRENT)
            }

            fusedLocationClient.requestLocationUpdates(locationRequest, pendingIntent)

        } else {
            // No location permission, display notification
            NotificationController.notifyLocationPermissionsRequired(validContext)
        }
    }

    companion object {
        const val ALERT_SERVICE_REQUEST = 301

        fun getIntent(context: Context) = Intent(context, AlertReceiver::class.java)
    }

}