package com.tragicfruit.weatherapp

import android.app.Application
import com.tragicfruit.weatherapp.utils.ViewHelper
import io.realm.Realm
import io.realm.RealmConfiguration

class WApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Realm.init(this)
        Realm.setDefaultConfiguration(RealmConfiguration.Builder()
                .name("WeatherAppDB")
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded() // TODO: remove on production
                .build())

        ViewHelper.init(resources)
    }

}