package com.tragicfruit.kindweather.utils.controllers

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.tragicfruit.kindweather.utils.PermissionHelper
import com.tragicfruit.kindweather.utils.SharedPrefsHelper
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar
import java.util.Date
import javax.inject.Inject
import timber.log.Timber

class AlertController @Inject constructor(
    private val sharedPrefsHelper: SharedPrefsHelper
) {

    private val calendar = Calendar.getInstance()

    fun scheduleDailyAlert(context: Context) {
        val alarmManager = ContextCompat.getSystemService(context, AlarmManager::class.java)

        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            ALERT_RECEIVER_REQUEST,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        calendar.timeInMillis = System.currentTimeMillis()
        calendar.set(Calendar.HOUR_OF_DAY, sharedPrefsHelper.getAlertHour())
        calendar.set(Calendar.MINUTE, sharedPrefsHelper.getAlertMinute())
        calendar.set(Calendar.SECOND, 0)

        if (calendar.time.before(Date())) {
            // If time is earlier today, schedule for tomorrow
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }

        Timber.i("Daily alert scheduled for ${calendar.time}")
        alarmManager?.setRepeating(
            AlarmManager.RTC,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )

        // Enable boot completed receiver
        val receiver = ComponentName(context, BootReceiver::class.java)
        context.packageManager.setComponentEnabledSetting(
            receiver,
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP
        )
    }

    companion object {
        private const val ALERT_RECEIVER_REQUEST = 300
    }
}

@AndroidEntryPoint
class AlarmReceiver : BroadcastReceiver() {

    @Inject lateinit var notificationController: NotificationController

    @SuppressWarnings("MissingPermission")
    override fun onReceive(context: Context?, intent: Intent?) {
        context ?: return

        if (PermissionHelper.hasBackgroundLocationPermission(context)) {
            Timber.d("Requesting current location")

            // Request current location
            val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
            val locationRequest = LocationRequest.create().setNumUpdates(1)

            val pendingIntent = PendingIntent.getBroadcast(
                context,
                ALERT_SERVICE_REQUEST,
                Intent(context, LocationReceiver::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT
            )

            fusedLocationClient.requestLocationUpdates(locationRequest, pendingIntent)
        } else {
            // No location permission, display notification
            notificationController.notifyLocationPermissionsRequired(context)
        }
    }

    companion object {
        private const val ALERT_SERVICE_REQUEST = 301
    }
}

class LocationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        AlertService.enqueueWork(context, intent)
    }
}
