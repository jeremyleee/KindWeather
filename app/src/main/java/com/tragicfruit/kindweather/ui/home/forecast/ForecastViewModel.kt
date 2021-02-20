package com.tragicfruit.kindweather.ui.home.forecast

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.ResultReceiver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.tragicfruit.kindweather.controllers.FetchAddressService
import com.tragicfruit.kindweather.data.ForecastRepository
import com.tragicfruit.kindweather.model.ForecastPeriod
import com.tragicfruit.kindweather.utils.SharedPrefsHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ForecastViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: ForecastRepository,
    private val sharedPrefsHelper: SharedPrefsHelper
) : ViewModel() {

    val useImperialUnits: Boolean
    get() = sharedPrefsHelper.usesImperialUnits()

    private val _forecast: MutableLiveData<ForecastPeriod> = MutableLiveData()
    val forecast: LiveData<ForecastPeriod> get() = _forecast

    private val _mainColor: MutableLiveData<Int> = MutableLiveData()
    val mainColor: LiveData<Int> get() = _mainColor

    private val _createdAt: MutableLiveData<Date> = MutableLiveData()
    val createdAt: LiveData<Date> get() = _createdAt

    private val _addressString: MutableLiveData<String?> = MutableLiveData()
    val addressString: LiveData<String?> get() = _addressString

    init {
        savedStateHandle.get<String>("forecastId")?.let { id ->
            _forecast.value = repository.findForecast(id)
        }

        _mainColor.value = savedStateHandle.get("color")
        _createdAt.value = savedStateHandle.get<Long>("timeCreatedMillis")?.let { Date(it) }
    }

    // TODO: refactor this with coroutines
    fun fetchAddress(context: Context, forecast: ForecastPeriod) {
        FetchAddressService.start(context, forecast.latitude, forecast.longitude,
            object : ResultReceiver(Handler(Looper.getMainLooper())) {
                override fun onReceiveResult(resultCode: Int, resultData: Bundle?) {
                    _addressString.postValue(resultData?.getString(FetchAddressService.RESULT_DATA))
                }
        })
    }
}
