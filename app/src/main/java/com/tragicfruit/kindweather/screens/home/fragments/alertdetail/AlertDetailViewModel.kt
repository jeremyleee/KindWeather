package com.tragicfruit.kindweather.screens.home.fragments.alertdetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.tragicfruit.kindweather.model.WeatherAlert
import com.tragicfruit.kindweather.model.WeatherAlertParam
import dagger.hilt.android.lifecycle.HiltViewModel
import io.realm.Realm
import javax.inject.Inject

@HiltViewModel
class AlertDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    // TODO: update when Hilt supports navArgs
    val alert = requireNotNull(
        savedStateHandle.get<Int>("alertId")?.let { id ->
            WeatherAlert.fromId(id, Realm.getDefaultInstance())
        }
    )

    val resetButtonEnabled: MutableLiveData<Boolean> by lazy { MutableLiveData() }
    val alertParams: MutableLiveData<List<WeatherAlertParam>> by lazy { MutableLiveData(alert.params) }

    fun enableAlert(enabled: Boolean) {
        Realm.getDefaultInstance().executeTransaction {
            WeatherAlert.setEnabled(alert, enabled, it)
        }
    }

    fun resetParams() {
        Realm.getDefaultInstance().executeTransaction { realm ->
            alert.params.forEach { param ->
                WeatherAlertParam.resetToDefault(param, realm)
            }
        }

        alertParams.value = alert.params
        resetButtonEnabled.value = false
    }

    fun onLowerBoundChanged(param: WeatherAlertParam, value: Double?) {
        Realm.getDefaultInstance().executeTransaction {
            WeatherAlertParam.setLowerBound(param, value, it)
        }

        resetButtonEnabled.value = true
    }

    fun onUpperBoundChanged(param: WeatherAlertParam, value: Double?) {
        Realm.getDefaultInstance().executeTransaction {
            WeatherAlertParam.setUpperBound(param, value, it)
        }

        resetButtonEnabled.value = true
    }
}