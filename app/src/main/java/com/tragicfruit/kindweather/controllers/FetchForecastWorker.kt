package com.tragicfruit.kindweather.controllers

import android.content.Context
import android.os.Looper
import androidx.concurrent.futures.CallbackToFutureAdapter
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.common.util.concurrent.ListenableFuture
import com.tragicfruit.kindweather.data.ForecastRepository
import com.tragicfruit.kindweather.utils.PermissionHelper
import timber.log.Timber
import javax.inject.Inject

/**
 * Regularly fetches forecast to keep local forecast data up to date
 */
class FetchForecastWorker(context: Context, workerParams: WorkerParameters) : ListenableWorker(context, workerParams) {

    @Inject lateinit var forecastRepository: ForecastRepository
    @Inject lateinit var notificationController: NotificationController

    @SuppressWarnings("MissingPermission")
    override fun startWork(): ListenableFuture<Result> {
        val callback = CallbackToFutureAdapter.Resolver<Result> { completer ->
            if (PermissionHelper.hasBackgroundLocationPermission(applicationContext)) {
                Timber.d("Requesting current location")


                // Request current location
                val fusedLocationClient = LocationServices.getFusedLocationProviderClient(applicationContext)
                val locationRequest = LocationRequest.create().setNumUpdates(1)

                fusedLocationClient.requestLocationUpdates(locationRequest, object : LocationCallback() {
                    override fun onLocationResult(result: LocationResult?) {
                        result?.lastLocation?.let { location ->
                            Timber.d("Found location: $location")

                            forecastRepository.fetchForecast(location.latitude, location.longitude) { success, code, message ->
                                if (success) {
                                    completer.set(Result.success())
                                } else {
                                    Timber.e("Code:$code; Message:$message")
                                    completer.set(Result.retry())
                                }
                            }
                        }
                    }
                }, Looper.myLooper())

            } else {
                // No location permission, display notification
                notificationController.notifyLocationPermissionsRequired(applicationContext)
                completer.set(Result.failure())
            }
        }

        return CallbackToFutureAdapter.getFuture(callback)
    }

    companion object {
        const val PERIODIC_FETCH_FORECAST_WORK = "periodic-fetch-forecast"
    }
}