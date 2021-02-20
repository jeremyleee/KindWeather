package com.tragicfruit.kindweather.ui.home.forecast

import android.location.Geocoder
import androidx.lifecycle.*
import com.tragicfruit.kindweather.data.NotificationRepository
import com.tragicfruit.kindweather.model.WeatherNotification
import com.tragicfruit.kindweather.utils.SharedPrefsHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class ForecastViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: NotificationRepository,
    private val sharedPrefsHelper: SharedPrefsHelper,
    private val geocoder: Geocoder
) : ViewModel() {

    val useImperialUnits: Boolean
    get() = sharedPrefsHelper.usesImperialUnits()

    private val _notification: MutableLiveData<WeatherNotification> = MutableLiveData()
    val notification: LiveData<WeatherNotification> get() = _notification

    val addressLabel: LiveData<String?> = Transformations.map(_notification) { fetchAddress(it) }

    init {
        savedStateHandle.get<String>("notificationId")?.let { id ->
            _notification.value = repository.findNotification(id)
        }
    }

    private fun fetchAddress(notification: WeatherNotification): String? {
        return try {
            val address = geocoder.getFromLocation(
                notification.latitude,
                notification.longitude,
                1
            ).firstOrNull()

            Timber.d("Fetched address ${address?.toString()}")
            address?.locality ?: address?.subAdminArea

        } catch (e: IOException) {
            null
        }
    }
}
