package com.tragicfruit.kindweather.ui.home.alertdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.tragicfruit.kindweather.data.AlertRepository
import com.tragicfruit.kindweather.data.model.WeatherAlert
import com.tragicfruit.kindweather.data.model.WeatherAlertParam
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AlertDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: AlertRepository
) : ViewModel() {

    private val _alert: MutableLiveData<WeatherAlert> by lazy { MutableLiveData() }
    val alert: LiveData<WeatherAlert> get() = _alert

    private val _resetButtonEnabled: MutableLiveData<Boolean> by lazy { MutableLiveData() }
    val resetButtonEnabled: LiveData<Boolean> get() = _resetButtonEnabled

    init {
        // TODO: update when Hilt supports navArgs
        savedStateHandle.get<Int>("alertId")?.let { id ->
            _alert.value = repository.findAlert(id)
        }
    }

    fun enableAlert(enabled: Boolean) {
        _alert.value?.let { repository.setAlertEnabled(it, enabled) }
    }

    fun resetParams() {
        _alert.value = _alert.value?.also { repository.resetParamsToDefault(it) }
        _resetButtonEnabled.value = false
    }

    fun updateLowerBound(param: WeatherAlertParam, value: Double?) {
        repository.setParamLowerBound(param, value)
        _resetButtonEnabled.value = true
    }

    fun updateUpperBound(param: WeatherAlertParam, value: Double?) {
        repository.setParamUpperBound(param, value)
        _resetButtonEnabled.value = true
    }
}
