package com.tragicfruit.kindweather.ui.home.forecast

import android.location.Geocoder
import androidx.lifecycle.*
import com.tragicfruit.kindweather.data.ForecastRepository
import com.tragicfruit.kindweather.model.ForecastPeriod
import com.tragicfruit.kindweather.utils.SharedPrefsHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import java.io.IOException
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ForecastViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: ForecastRepository,
    private val sharedPrefsHelper: SharedPrefsHelper,
    private val geocoder: Geocoder
) : ViewModel() {

    val useImperialUnits: Boolean
    get() = sharedPrefsHelper.usesImperialUnits()

    private val _forecast: MutableLiveData<ForecastPeriod> = MutableLiveData()
    val forecast: LiveData<ForecastPeriod> get() = _forecast

    private val _mainColor: MutableLiveData<Int> = MutableLiveData()
    val mainColor: LiveData<Int> get() = _mainColor

    private val _createdAt: MutableLiveData<Date> = MutableLiveData()
    val createdAt: LiveData<Date> get() = _createdAt

    val addressLabel: LiveData<String?> = Transformations.map(_forecast) { fetchAddress(it) }

    init {
        savedStateHandle.get<String>("forecastId")?.let { id ->
            _forecast.value = repository.findForecast(id)
        }

        _mainColor.value = savedStateHandle.get("color")
        _createdAt.value = savedStateHandle.get<Long>("timeCreatedMillis")?.let { Date(it) }
    }

    private fun fetchAddress(forecast: ForecastPeriod): String? {
        return try {
            val address = geocoder.getFromLocation(
                forecast.latitude,
                forecast.longitude,
                1
            ).firstOrNull()

            Timber.d("Fetched address ${address?.toString()}")
            address?.locality ?: address?.subAdminArea

        } catch (e: IOException) {
            null
        }
    }
}
