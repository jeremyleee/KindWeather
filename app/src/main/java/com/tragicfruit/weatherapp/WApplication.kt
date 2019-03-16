package com.tragicfruit.weatherapp

import android.app.Application
import com.tragicfruit.weatherapp.controllers.NotificationController
import com.tragicfruit.weatherapp.controllers.WeatherController
import com.tragicfruit.weatherapp.model.ForecastType
import com.tragicfruit.weatherapp.model.WeatherAlert
import com.tragicfruit.weatherapp.utils.SharedPrefsHelper
import com.tragicfruit.weatherapp.utils.ViewHelper
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.kotlin.where
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

        // Adding some test alerts
        // TODO: shift this to a controller
        val realm = Realm.getDefaultInstance()
        if (realm.where<WeatherAlert>().count() == 0L) {
            realm.executeTransaction {
                val umbrellaAlert = WeatherAlert.create("Umbrella", "Remember to bring your umbrella!", 1, it)
                WeatherAlert.addParam(umbrellaAlert, ForecastType.Rain_probability, 0.5, null, it)
                WeatherAlert.addParam(umbrellaAlert, ForecastType.Rain_intensity, 10.0, null, it)
                WeatherAlert.addParam(umbrellaAlert, ForecastType.Wind_gust, null, 40.0, it)

                val rainJacketAlert = WeatherAlert.create("Rain jacket", "Bring your rain jacket today!", 2, it)
                WeatherAlert.addParam(rainJacketAlert, ForecastType.Rain_probability, 0.5, null, it)
                WeatherAlert.addParam(rainJacketAlert, ForecastType.Rain_intensity, 10.0, null, it)
                WeatherAlert.addParam(rainJacketAlert, ForecastType.Wind_gust, 40.0, null, it)

                val sunscreenAlert = WeatherAlert.create("Sunscreen", "Put on some sunscreen before you go out", 3, it)
                WeatherAlert.addParam(sunscreenAlert, ForecastType.Uv_index, 3.0, null, it)
            }
        }

    }

}