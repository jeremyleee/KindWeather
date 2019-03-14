package com.tragicfruit.weatherapp.controllers

import android.Manifest
import android.content.Context
import android.os.Looper
import androidx.concurrent.futures.CallbackToFutureAdapter
import androidx.work.*
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.common.util.concurrent.ListenableFuture
import com.tragicfruit.weatherapp.utils.PermissionHelper
import timber.log.Timber
import java.util.concurrent.TimeUnit

class FetchForecastWorker(context: Context, workerParams: WorkerParameters) : ListenableWorker(context, workerParams) {

    @SuppressWarnings("MissingPermission")
    override fun startWork(): ListenableFuture<Result> {
        val callback = CallbackToFutureAdapter.Resolver<Result> { completer ->
            if (PermissionHelper.hasPermission(applicationContext, Manifest.permission.ACCESS_FINE_LOCATION)) {
                Timber.d("Requesting current location")


                // Request current location
                val fusedLocationClient = LocationServices.getFusedLocationProviderClient(applicationContext)
                val locationRequest = LocationRequest.create().setNumUpdates(1)

                fusedLocationClient.requestLocationUpdates(locationRequest, object : LocationCallback() {
                    override fun onLocationResult(result: LocationResult?) {
                        result?.lastLocation?.let { location ->
                            Timber.d("Found location: $location")

                            WeatherController.fetchForecast(location.latitude, location.longitude) { success, code, message ->
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
                NotificationController.notifyLocationPermissionsRequired(applicationContext)
                completer.set(Result.failure())
            }
        }

        return CallbackToFutureAdapter.getFuture(callback)
    }

    companion object {
        private const val PERIODIC_FETCH_FORECAST_WORK = "periodic-fetch-forecast"

        fun enqueueWork() {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            val request = PeriodicWorkRequestBuilder<FetchForecastWorker>(6, TimeUnit.HOURS)
                .setConstraints(constraints)
                .build()

            WorkManager.getInstance()
                .enqueueUniquePeriodicWork(PERIODIC_FETCH_FORECAST_WORK, ExistingPeriodicWorkPolicy.REPLACE, request)
        }
    }

}