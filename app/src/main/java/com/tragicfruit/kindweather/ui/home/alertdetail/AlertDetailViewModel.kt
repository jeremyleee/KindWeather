package com.tragicfruit.kindweather.ui.home.alertdetail

import androidx.lifecycle.*
import com.tragicfruit.kindweather.data.AlertRepository
import com.tragicfruit.kindweather.data.model.WeatherAlertParam
import com.tragicfruit.kindweather.data.model.WeatherAlertWithParams
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlertDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: AlertRepository
) : ViewModel() {

    // TODO: update when Hilt supports navArgs
    private val alertId = requireNotNull(savedStateHandle.get<String>("alertId"))

    val alertWithParams: LiveData<WeatherAlertWithParams> = liveData {
        emit(repository.findParamsForAlert(alertId))
    }
    val resetButtonEnabled: LiveData<Boolean> get() = alertWithParams.map {
        it.params.any { param -> param.isEdited() }
    }

    fun enableAlert(enabled: Boolean) {
        viewModelScope.launch {
            alertWithParams.value?.let { repository.setAlertEnabled(it.alert, enabled) }
        }
    }

    fun resetParams() {
        viewModelScope.launch {
            alertWithParams.value?.let { repository.resetParamsToDefault(it.params) }
        }
    }

    fun updateLowerBound(param: WeatherAlertParam, value: Double?) {
        viewModelScope.launch {
            repository.setParamLowerBound(param, value)
        }
    }

    fun updateUpperBound(param: WeatherAlertParam, value: Double?) {
        viewModelScope.launch {
            repository.setParamUpperBound(param, value)
        }
    }
}
