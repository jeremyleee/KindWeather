package com.tragicfruit.weatherapp.controllers

import android.Manifest
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.tragicfruit.weatherapp.utils.PermissionHelper
import timber.log.Timber
import java.util.*

object AlertController {

    private const val ALERT_RECEIVER_REQUEST = 300

    private val calendar = Calendar.getInstance().apply {
        timeInMillis = System.currentTimeMillis()
        set(Calendar.HOUR_OF_DAY, 7)
        set(Calendar.MINUTE, 0)
    }

    fun scheduleDailyAlert(context: Context) {
        val alarmManager = ContextCompat.getSystemService(context, AlarmManager::class.java)

        val intent = Intent(context, AlertReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, ALERT_RECEIVER_REQUEST, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        alarmManager?.setRepeating(AlarmManager.RTC, calendar.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent)
    }

}

class AlertReceiver : BroadcastReceiver() {

    @SuppressWarnings("MissingPermission")
    override fun onReceive(context: Context?, intent: Intent?) {
        Timber.wtf("received")
        val context = context ?: return

        if (PermissionHelper.hasPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)) {
            Timber.d("Requesting current location")

            // Request current location
            val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
            val locationRequest = LocationRequest.create().setNumUpdates(1)

            val serviceIntent = Intent(context, AlertService::class.java)
            val pendingIntent = PendingIntent.getService(context, ALERT_SERVICE_REQUEST, serviceIntent, PendingIntent.FLAG_UPDATE_CURRENT)

            fusedLocationClient.requestLocationUpdates(locationRequest, pendingIntent)

        } else {
            // No location permission, display notification
            NotificationController.notifyLocationPermissionsRequired(context)
        }
    }

    companion object {
        const val ALERT_SERVICE_REQUEST = 301
    }

}