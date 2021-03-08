package com.tragicfruit.kindweather.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tragicfruit.kindweather.data.ForecastDataType
import com.tragicfruit.kindweather.data.WeatherAlertType
import com.tragicfruit.kindweather.data.source.AlertRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class MainViewModel @Inject constructor(
    private val alertRepository: AlertRepository
) : ViewModel() {

    // TODO: replace with init file from assets
    fun createInitialAlerts() {
        viewModelScope.launch {
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
