package com.tragicfruit.kindweather.ui.home.alertdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.tragicfruit.kindweather.data.WeatherAlertParam
import com.tragicfruit.kindweather.data.WeatherAlertWithParams
import com.tragicfruit.kindweather.data.source.AlertRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

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
