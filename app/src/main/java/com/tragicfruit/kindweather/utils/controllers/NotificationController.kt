package com.tragicfruit.kindweather.utils.controllers

import android.annotation.TargetApi
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.annotation.StringRes
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavDeepLinkBuilder
import com.tragicfruit.kindweather.R
import com.tragicfruit.kindweather.data.WeatherNotification
import com.tragicfruit.kindweather.ui.home.forecast.ForecastFragmentArgs
import javax.inject.Inject

class NotificationController @Inject constructor() {

    fun setupNotificationChannels(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Location notifications
            createNotificationChannel(context,
                R.string.notification_channel_location,
                LOCATION_CHANNEL_ID)

            // Weather notifications
            createNotificationChannel(context,
                R.string.notification_channel_weather,
                WEATHER_CHANNEL_ID,
                NotificationManager.IMPORTANCE_HIGH)
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(context: Context, @StringRes name: Int, id: String,
                                          importance: Int = NotificationManager.IMPORTANCE_DEFAULT) {
        val channel = NotificationChannel(id, context.getString(name), importance)
        val notificationManager = ContextCompat.getSystemService(context, NotificationManager::class.java)
        notificationManager?.createNotificationChannel(channel)
    }

    fun notifyLocationPermissionsRequired(context: Context) {
        val intent = Intent().apply {
            action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            data = Uri.fromParts("package", context.packageName, null)
        }

        val pendingIntent = PendingIntent.getActivity(context, APP_SETTINGS_ACTION, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val builder = NotificationCompat.Builder(context, LOCATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(context.getString(R.string.notification_location_permission_title))
            .setContentText(context.getString(R.string.notification_location_permission_text))
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText(context.getString(R.string.notification_location_permission_text)))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setColor(ContextCompat.getColor(context, R.color.colorPrimary))

        NotificationManagerCompat.from(context)
            .notify(LOCATION_PERMISSIONS_ID, builder.build())
    }

    fun notifyWeatherAlert(context: Context, notification: WeatherNotification) {
        val arguments = ForecastFragmentArgs(notification.id).toBundle()

        val pendingIntent = NavDeepLinkBuilder(context)
            .setGraph(R.navigation.home_nav_graph)
            .setDestination(R.id.forecastFragment)
            .setArguments(arguments)
            .createPendingIntent()

        val builder = NotificationCompat.Builder(context, WEATHER_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(notification.description)
            .setContentText(context.getString(R.string.notification_weather_tap))
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText(context.getString(R.string.notification_weather_tap)))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setColor(ContextCompat.getColor(context, R.color.colorPrimary))

        NotificationManagerCompat.from(context)
            .notify(WEATHER_ALERT_ID, builder.build())
    }

    companion object {
        private const val LOCATION_CHANNEL_ID = "location"
        private const val WEATHER_CHANNEL_ID = "weather"

        private const val LOCATION_PERMISSIONS_ID = 600
        private const val WEATHER_ALERT_ID = 601

        private const val APP_SETTINGS_ACTION = 1255
    }
}