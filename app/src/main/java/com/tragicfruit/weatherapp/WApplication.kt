package com.tragicfruit.weatherapp

import android.app.Application
import androidx.core.content.ContextCompat
import com.tragicfruit.weatherapp.controllers.NotificationController
import com.tragicfruit.weatherapp.controllers.WeatherController
import com.tragicfruit.weatherapp.model.ForecastType
import com.tragicfruit.weatherapp.model.WeatherAlert
import com.tragicfruit.weatherapp.utils.ColorHelper
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
                val umbrellaAlert = WeatherAlert.create("Bring an\numbrella", "Remember to bring your umbrella!",
                    1, ContextCompat.getColor(this, R.color.alert_umbrella), "umbrella", it)
                WeatherAlert.addParam(umbrellaAlert, ForecastType.Rain_probability, 0.5, null, it)
                WeatherAlert.addParam(umbrellaAlert, ForecastType.Wind_gust, null, 10.8, it)

                val rainJacketAlert = WeatherAlert.create("Wear a\nthick jacket", "Bring your rain jacket today!",
                    2, ContextCompat.getColor(this, R.color.alert_rain_jacket), "jacket", it)
                WeatherAlert.addParam(rainJacketAlert, ForecastType.Rain_probability, 0.5, null, it)
                WeatherAlert.addParam(rainJacketAlert, ForecastType.Wind_gust, 10.8, null, it)

                val sunscreenAlert = WeatherAlert.create("Sunscreen", "Put on some sunscreen before you go out",
                    3, ColorHelper.getRandomColor(), "sunscreen", it)
                WeatherAlert.addParam(sunscreenAlert, ForecastType.Uv_index, 3.0, null, it)
            }
        }

    }

}