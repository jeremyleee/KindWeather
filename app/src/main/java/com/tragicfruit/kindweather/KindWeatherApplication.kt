package com.tragicfruit.kindweather

import android.app.Application
import com.tragicfruit.kindweather.util.controllers.NotificationController
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject
import timber.log.Timber

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
