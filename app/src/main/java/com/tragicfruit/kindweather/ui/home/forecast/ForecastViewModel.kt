package com.tragicfruit.kindweather.ui.home.forecast

import android.location.Geocoder
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.tragicfruit.kindweather.data.WeatherNotification
import com.tragicfruit.kindweather.data.source.NotificationRepository
import com.tragicfruit.kindweather.util.SharedPrefsHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.IOException
import javax.inject.Inject
import timber.log.Timber

@HiltViewModel
class ForecastViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    repository: NotificationRepository,
    private val sharedPrefsHelper: SharedPrefsHelper,
    private val geocoder: Geocoder
) : ViewModel() {

    // TODO: update when Hilt supports navArgs
    private val notificationId = requireNotNull(savedStateHandle.get<String>("notificationId"))

    val useImperialUnits: Boolean
        get() = sharedPrefsHelper.usesImperialUnits()

    val notification: LiveData<WeatherNotification> = liveData {
        emit(repository.findNotification(notificationId))
    }
    val addressLabel: LiveData<String?> = notification.map { fetchAddress(it) }

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
