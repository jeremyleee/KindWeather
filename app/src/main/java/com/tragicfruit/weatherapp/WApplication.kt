package com.tragicfruit.weatherapp

import android.app.Application
import com.tragicfruit.weatherapp.controllers.NotificationController
import com.tragicfruit.weatherapp.controllers.WeatherController
import com.tragicfruit.weatherapp.utils.SharedPrefsHelper
import com.tragicfruit.weatherapp.utils.ViewHelper
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
                .name("WeatherAppDB")
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded() // TODO: remove on production
                .build())

        SharedPrefsHelper.init(this)
        ViewHelper.init(this)

        WeatherController.init()
        NotificationController.init(this)

    }

}