package com.tragicfruit.kindweather

import android.app.Application
import com.tragicfruit.kindweather.data.AlertRepository
import com.tragicfruit.kindweather.data.model.ForecastDataType
import com.tragicfruit.kindweather.data.model.WeatherAlertType
import com.tragicfruit.kindweather.utils.controllers.NotificationController
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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

        notificationController.setupNotificationChannels(this)
        createInitialAlerts()
    }

    // TODO: replace with init file from assets
    private fun createInitialAlerts() {
        CoroutineScope(Dispatchers.IO).launch {
            if (alertRepository.getAlertCount() > 0) {
                // Alerts already created
                return@launch
            }

            alertRepository.createAlert(1, WeatherAlertType.Umbrella).also {
                alertRepository.createParam(
                    alert = it,
                    type = ForecastDataType.PrecipProbability,
                    rawDefaultLowerBound = 0.5,
                    rawDefaultUpperBound = null
                )
                alertRepository.createParam(
                    alert = it,
                    type = ForecastDataType.WindGust,
                    rawDefaultLowerBound = null,
                    rawDefaultUpperBound = 10.8
                )
            }

            alertRepository.createAlert(2, WeatherAlertType.Jacket).also {
                alertRepository.createParam(
                    alert = it,
                    type = ForecastDataType.TempHigh,
                    rawDefaultLowerBound = 4.0,
                    rawDefaultUpperBound = 12.0
                )
            }

            alertRepository.createAlert(3, WeatherAlertType.TShirt).also {
                alertRepository.createParam(
                    alert = it,
                    type = ForecastDataType.TempHigh,
                    rawDefaultLowerBound = 25.0,
                    rawDefaultUpperBound = null
                )
            }

            alertRepository.createAlert(4, WeatherAlertType.Sunscreen).also {
                alertRepository.createParam(
                    alert = it,
                    type = ForecastDataType.UVIndex,
                    rawDefaultLowerBound = 6.0,
                    rawDefaultUpperBound = null
                )
            }

            alertRepository.createAlert(5, WeatherAlertType.RainJacket).also {
                alertRepository.createParam(
                    alert = it,
                    type = ForecastDataType.PrecipProbability,
                    rawDefaultLowerBound = 0.5,
                    rawDefaultUpperBound = null
                )
                alertRepository.createParam(
                    alert = it,
                    type = ForecastDataType.WindGust,
                    rawDefaultLowerBound = 10.8,
                    rawDefaultUpperBound = null
                )
            }

            alertRepository.createAlert(6, WeatherAlertType.ThickJacket).also {
                alertRepository.createParam(
                    alert = it,
                    type = ForecastDataType.TempHigh,
                    rawDefaultLowerBound = null,
                    rawDefaultUpperBound = 4.0
                )
            }
        }
    }

}