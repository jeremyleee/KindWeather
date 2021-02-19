package com.tragicfruit.kindweather.screens.home.fragments.alertdetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.tragicfruit.kindweather.data.AlertRepository
import com.tragicfruit.kindweather.model.WeatherAlertParam
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AlertDetailViewModel @Inject constructor(
    private val alertRepository: AlertRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    // TODO: update when Hilt supports navArgs
    val alert = requireNotNull(
        savedStateHandle.get<Int>("alertId")?.let { id ->
            alertRepository.findAlert(id)
        }
    )

    val resetButtonEnabled: MutableLiveData<Boolean> by lazy { MutableLiveData() }
    val alertParams: MutableLiveData<List<WeatherAlertParam>> by lazy { MutableLiveData(alert.params) }

    fun enableAlert(enabled: Boolean) {
        alertRepository.setAlertEnabled(alert, enabled)
    }

    fun resetParams() {
        alert.params.forEach { param ->
            alertRepository.resetParamsToDefault(param)
        }

        alertParams.value = alert.params
        resetButtonEnabled.value = false
    }

    fun onLowerBoundChanged(param: WeatherAlertParam, value: Double?) {
        alertRepository.setParamLowerBound(param, value)
        resetButtonEnabled.value = true
    }

    fun onUpperBoundChanged(param: WeatherAlertParam, value: Double?) {
        alertRepository.setParamUpperBound(param, value)
        resetButtonEnabled.value = true
    }
}
