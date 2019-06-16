package com.tragicfruit.kindweather.controllers

import androidx.annotation.DrawableRes
import com.tragicfruit.kindweather.R

data class ForecastResponse(val latitude: Double, val longitude: Double, val timezone: String, val daily: Daily) {

    data class Daily(val summary: String?, val icon: String?, val data: List<DataPoint>) {

        data class DataPoint(val time: Long,
                             val summary: String?,
                             val icon: String?,
                             val precipIntensity: Double?,
                             val precipProbability: Double?,
                             val precipType: String?,
                             val temperatureHigh: Double?,
                             val temperatureLow: Double?,
                             val dewPoint: Double?,
                             val humidity: Double?,
                             val pressure: Double?,
                             val windSpeed: Double?,
                             val windGust: Double?,
                             val cloudCover: Double?,
                             val uvIndex: Int?,
                             val visibility: Double?,
                             val ozone: Double?)

    }

}

enum class ForecastIcon(@DrawableRes val iconRes: Int = 0) {
    clearday(R.drawable.ic_sunny),
    clearnight(R.drawable.ic_sunny),
    rain(R.drawable.ic_rain),
    snow(R.drawable.ic_snow),
    sleet(R.drawable.ic_snow),
    wind(R.drawable.ic_windy),
    fog(R.drawable.ic_fog),
    cloudy(R.drawable.ic_cloudy),
    partlycloudyday(R.drawable.ic_partly_cloudy),
    partlycloudynight(R.drawable.ic_partly_cloudy),

    unknown;

    companion object {
        fun fromString(type: String?) = try {
            ForecastIcon.valueOf(type!!.replace("-", ""))
        } catch (e: Exception) {
            unknown
        }
    }

}