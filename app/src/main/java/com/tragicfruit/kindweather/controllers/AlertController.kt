package com.tragicfruit.kindweather.controllers

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.tragicfruit.kindweather.utils.PermissionHelper
import com.tragicfruit.kindweather.utils.SharedPrefsHelper
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class AlertController @Inject constructor() {

    private val calendar = Calendar.getInstance()

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

        // Enable boot completed receiver
        val receiver = ComponentName(context, BootReceiver::class.java)
        context.packageManager.setComponentEnabledSetting(
            receiver,
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP)
    }

    companion object {
        private const val ALERT_RECEIVER_REQUEST = 300
    }
}

@AndroidEntryPoint
class AlertReceiver : BroadcastReceiver() {

    @Inject lateinit var notificationController: NotificationController

    @SuppressWarnings("MissingPermission")
    override fun onReceive(context: Context?, intent: Intent?) {
        context ?: return

        if (PermissionHelper.hasBackgroundLocationPermission(context)) {
            Timber.d("Requesting current location")

            // Request current location
            val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
            val locationRequest = LocationRequest.create().setNumUpdates(1)

            val serviceIntent = AlertService.getIntent(context)
            val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                PendingIntent.getForegroundService(context, ALERT_SERVICE_REQUEST, serviceIntent, PendingIntent.FLAG_UPDATE_CURRENT)
            } else {
                PendingIntent.getService(context, ALERT_SERVICE_REQUEST, serviceIntent, PendingIntent.FLAG_UPDATE_CURRENT)
            }

            fusedLocationClient.requestLocationUpdates(locationRequest, pendingIntent)

        } else {
            // No location permission, display notification
            notificationController.notifyLocationPermissionsRequired(context)
        }
    }

    companion object {
        const val ALERT_SERVICE_REQUEST = 301

        fun getIntent(context: Context) = Intent(context, AlertReceiver::class.java)
    }

}

@AndroidEntryPoint
class BootReceiver : BroadcastReceiver() {

    @Inject lateinit var alertController: AlertController

    override fun onReceive(context: Context?, intent: Intent?) {
        context ?: return

        if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {
            alertController.scheduleDailyAlert(context)
        }
    }

}