package com.tragicfruit.kindweather

import android.app.Application
import androidx.core.content.ContextCompat
import com.tragicfruit.kindweather.controllers.NotificationController
import com.tragicfruit.kindweather.controllers.WeatherController
import com.tragicfruit.kindweather.model.ForecastType
import com.tragicfruit.kindweather.model.WeatherAlert
import com.tragicfruit.kindweather.utils.ColorHelper
import com.tragicfruit.kindweather.utils.SharedPrefsHelper
import com.tragicfruit.kindweather.utils.ViewHelper
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
                .name("KindWeatherDB")
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
                val umbrellaAlert = WeatherAlert.create(1, "Bring an\numbrella", "Remember to bring your umbrella!",
                    1, ContextCompat.getColor(this, R.color.alert_umbrella), "umbrella", it)
                WeatherAlert.addParam(umbrellaAlert, ForecastType.RAIN_PROBABILITY, 0.5, null, it)
                WeatherAlert.addParam(umbrellaAlert, ForecastType.WIND_GUST, null, 10.8, it)

                val rainJacketAlert = WeatherAlert.create(2, "Wear a\nthick jacket", "Bring your rain jacket today!",
                    2, ContextCompat.getColor(this, R.color.alert_rain_jacket), "jacket", it)
                WeatherAlert.addParam(rainJacketAlert, ForecastType.RAIN_PROBABILITY, 0.5, null, it)
                WeatherAlert.addParam(rainJacketAlert, ForecastType.WIND_GUST, 10.8, null, it)

                val sunscreenAlert = WeatherAlert.create(3, "Sunscreen", "Put on some sunscreen before you go out",
                    3, ColorHelper.getRandomColor(), "sunscreen", it)
                WeatherAlert.addParam(sunscreenAlert, ForecastType.UV_INDEX, 6.0, null, it)
            }
        }

    }

}