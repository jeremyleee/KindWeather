package com.tragicfruit.kindweather

import android.app.Application
import com.tragicfruit.kindweather.utils.controllers.NotificationController
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class KindWeatherApplication : Application() {

    @Inject lateinit var notificationController: NotificationController

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        notificationController.setupNotificationChannels(this)
    }
}