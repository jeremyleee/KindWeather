package com.tragicfruit.kindweather

import android.app.Application
import com.tragicfruit.kindweather.controllers.NotificationController
import com.tragicfruit.kindweather.data.AlertRepository
import com.tragicfruit.kindweather.model.ForecastType
import com.tragicfruit.kindweather.model.WeatherAlert
import com.tragicfruit.kindweather.utils.SharedPrefsHelper
import com.tragicfruit.kindweather.utils.ViewHelper
import dagger.hilt.android.HiltAndroidApp
import io.realm.Realm
import io.realm.RealmConfiguration
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class KindWeatherApplication : Application() {

    @Inject lateinit var alertRepository: AlertRepository
    @Inject lateinit var notificationController: NotificationController

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        Realm.init(this)
        Realm.setDefaultConfiguration(RealmConfiguration.Builder()
            .name("KindWeatherDB")
            .schemaVersion(1)
            .allowWritesOnUiThread(true)
            .deleteRealmIfMigrationNeeded() // TODO: remove on production
            .build())

        SharedPrefsHelper.init(this)
        ViewHelper.init(this)

        notificationController.setupNotificationChannels(this)

        createInitialAlerts()
    }

    private fun createInitialAlerts() {
        if (alertRepository.getAlertCount() > 0) {
            // Alerts already created
            return
        }

        Realm.getDefaultInstance().executeTransaction { realm ->
            alertRepository.createAlert(1, WeatherAlert.Info.UMBRELLA, realm).also {
                alertRepository.createParam(it, ForecastType.PRECIP_PROBABILITY, 0.5, null, realm)
                alertRepository.createParam(it, ForecastType.WIND_GUST, null, 10.8, realm)
            }

            alertRepository.createAlert(2, WeatherAlert.Info.JACKET, realm).also {
                alertRepository.createParam(it, ForecastType.TEMP_HIGH, 4.0, 12.0, realm)
            }

            alertRepository.createAlert(3, WeatherAlert.Info.TSHIRT, realm).also {
                alertRepository.createParam(it, ForecastType.TEMP_HIGH, 25.0, null, realm)
            }

            alertRepository.createAlert(4, WeatherAlert.Info.SUNSCREEN, realm).also {
                alertRepository.createParam(it, ForecastType.UV_INDEX, 6.0, null, realm)
            }

            alertRepository.createAlert(5, WeatherAlert.Info.RAIN_JACKET, realm).also {
                alertRepository.createParam(it, ForecastType.PRECIP_PROBABILITY, 0.5, null, realm)
                alertRepository.createParam(it, ForecastType.WIND_GUST, 10.8, null, realm)
            }

            alertRepository.createAlert(6, WeatherAlert.Info.THICK_JACKET, realm).also {
                alertRepository.createParam(it, ForecastType.TEMP_HIGH, null, 4.0, realm)
            }
        }
    }

}