package com.tragicfruit.weatherapp.controllers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.tragicfruit.weatherapp.R

object NotificationController {

    private const val LOCATION_CHANNEL_ID = "location"

    private const val LOCATION_PERMISSIONS_ID = 600

    fun init(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Location notifications
            val name = context.getString(R.string.notification_channel_location)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(LOCATION_CHANNEL_ID, name, importance)

            val notificationManager = ContextCompat.getSystemService(context, NotificationManager::class.java)
            notificationManager?.createNotificationChannel(channel)
        }
    }

    fun notifyLocationPermissionsRequired(context: Context) {
        val builder = NotificationCompat.Builder(context, LOCATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(context.getString(R.string.notification_location_permission_title))
            .setContentText(context.getString(R.string.notification_location_permission_text))
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText(context.getString(R.string.notification_location_permission_text)))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        // TODO: setup pending intent

        NotificationManagerCompat.from(context)
            .notify(LOCATION_PERMISSIONS_ID, builder.build())
    }

}