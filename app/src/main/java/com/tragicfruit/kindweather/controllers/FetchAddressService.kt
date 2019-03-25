package com.tragicfruit.kindweather.controllers

import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.location.Geocoder
import android.os.Bundle
import android.os.ResultReceiver
import com.tragicfruit.kindweather.R
import java.util.*

class FetchAddressService : IntentService(FetchAddressService::javaClass.name) {

    private var receiver: ResultReceiver? = null

    override fun onHandleIntent(intent: Intent?) {
        val extras = intent?.extras ?: return

        receiver = extras.getParcelable(RECEIVER_KEY) as? ResultReceiver
        val latitude = extras.getDouble(LATITUDE_KEY)
        val longitude = extras.getDouble(LONGITUDE_KEY)

        val geocoder = Geocoder(this, Locale.getDefault())
        val addresses = geocoder.getFromLocation(latitude, longitude, 1)

        addresses.firstOrNull()?.let {
            deliverResultToReceiver(SUCCESS_RESULT, it.locality)
        } ?: run {
            deliverResultToReceiver(FAILURE_RESULT, getString(R.string.forecast_location_not_found, latitude, longitude))
        }
    }

    private fun deliverResultToReceiver(resultCode: Int, message: String) {
        val bundle = Bundle().apply { putString(RESULT_DATA, message) }
        receiver?.send(resultCode, bundle)
    }

    companion object {
        private const val LATITUDE_KEY = "fetch-lat"
        private const val LONGITUDE_KEY = "fetch-lon"
        private const val RECEIVER_KEY = "fetch-receiver"

        const val SUCCESS_RESULT = 0
        const val FAILURE_RESULT = 1
        const val RESULT_DATA = "result-data"

        fun start(context: Context, latitude: Double, longitude: Double, receiver: ResultReceiver) {
            context.startService(Intent(context, FetchAddressService::class.java).apply {
                putExtra(LATITUDE_KEY, latitude)
                putExtra(LONGITUDE_KEY, longitude)
                putExtra(RECEIVER_KEY, receiver)
            })
        }
    }

}