package com.tragicfruit.kindweather

import android.app.Application
import com.tragicfruit.kindweather.controllers.AlertController
import com.tragicfruit.kindweather.controllers.NotificationController
import com.tragicfruit.kindweather.controllers.WeatherController
import com.tragicfruit.kindweather.utils.SharedPrefsHelper
import com.tragicfruit.kindweather.utils.ViewHelper
import io.realm.Realm
import io.realm.RealmConfiguration
import timber.log.Timber

class WApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        Realm.init(this)
        Realm.setDefaultConfiguration(RealmConfiguration.Builder()
                .name("KindWeatherDB")
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded() // TODO: remove on production
                .build())

        SharedPrefsHelper.init(this)
        ViewHelper.init(this)

        WeatherController.init()
        NotificationController.init(this)

        AlertController.createAlerts()
    }

}