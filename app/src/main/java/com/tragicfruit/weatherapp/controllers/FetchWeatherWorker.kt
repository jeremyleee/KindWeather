package com.tragicfruit.weatherapp.controllers

import android.Manifest
import android.content.Context
import android.os.Looper
import androidx.concurrent.futures.ResolvableFuture
import androidx.work.*
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.common.util.concurrent.ListenableFuture
import com.tragicfruit.weatherapp.utils.PermissionHelper
import timber.log.Timber
import java.util.concurrent.TimeUnit

class FetchWeatherWorker(context: Context, workerParams: WorkerParameters) : ListenableWorker(context, workerParams) {

    @SuppressWarnings("MissingPermission")
    override fun startWork(): ListenableFuture<Result> {
        val future = ResolvableFuture.create<Result>()

        if (PermissionHelper.hasPermission(applicationContext, Manifest.permission.ACCESS_FINE_LOCATION)) {
            Timber.d("Requesting current location")

            // Request current location
            val fusedLocationClient = LocationServices.getFusedLocationProviderClient(applicationContext)
            val locationRequest = LocationRequest.create().setNumUpdates(1)

            fusedLocationClient.requestLocationUpdates(locationRequest, object : LocationCallback() {
                override fun onLocationResult(result: LocationResult?) {
                    result?.lastLocation?.let { location ->
                        Timber.d("Found location: $location")

                        WeatherController.deleteOldForecasts()
                        WeatherController.fetchForecast(location.latitude, location.longitude) { success, code, message ->
                            if (success) {
                                future.set(Result.success())
                            } else {
                                future.set(Result.retry())
                            }
                        }
                    }
                }
            }, Looper.myLooper())

        } else {
            // No location permission, display notification
            NotificationController.notifyLocationPermissionsRequired(applicationContext)
            future.set(Result.failure())
        }

        return future

    }

    companion object {
        fun enqueueWork() {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            val request = PeriodicWorkRequestBuilder<FetchWeatherWorker>(3, TimeUnit.HOURS)
                .setConstraints(constraints)
                .build()

            WorkManager.getInstance().enqueue(request)
        }
    }

}