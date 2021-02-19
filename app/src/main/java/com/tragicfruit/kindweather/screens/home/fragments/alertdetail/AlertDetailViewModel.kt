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
    private val repository: AlertRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    // TODO: update when Hilt supports navArgs
    val alert = requireNotNull(
        savedStateHandle.get<Int>("alertId")?.let { id ->
            repository.findAlert(id)
        }
    )

    val resetButtonEnabled: MutableLiveData<Boolean> by lazy { MutableLiveData() }
    val alertParams: MutableLiveData<List<WeatherAlertParam>> by lazy { MutableLiveData(alert.params) }

    fun enableAlert(enabled: Boolean) {
        repository.setAlertEnabled(alert, enabled)
    }

    fun resetParams() {
        alert.params.forEach { param ->
            repository.resetParamsToDefault(param)
        }

        alertParams.value = alert.params
        resetButtonEnabled.value = false
    }

    fun updateLowerBound(param: WeatherAlertParam, value: Double?) {
        repository.setParamLowerBound(param, value)
        resetButtonEnabled.value = true
    }

    fun updateUpperBound(param: WeatherAlertParam, value: Double?) {
        repository.setParamUpperBound(param, value)
        resetButtonEnabled.value = true
    }
}
