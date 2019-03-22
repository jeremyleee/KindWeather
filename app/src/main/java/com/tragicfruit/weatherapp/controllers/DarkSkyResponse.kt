package com.tragicfruit.weatherapp.controllers

import androidx.annotation.DrawableRes
import com.tragicfruit.weatherapp.R

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
    clearday(R.drawable.ic_sun),
    clearnight(R.drawable.ic_moon),
    rain(R.drawable.ic_umbrella),
    snow(R.drawable.ic_snow),
    sleet(R.drawable.ic_umbrella),
    wind(R.drawable.ic_forecast),
    fog(R.drawable.ic_cloud_fog),
    cloudy(R.drawable.ic_cloud),
    partlycloudyday(R.drawable.ic_cloud_sun),
    partlycloudynight(R.drawable.ic_cloud_moon),

    unknown;

    companion object {
        fun fromString(type: String) = try {
            ForecastIcon.valueOf(type.replace("-", ""))
        } catch (e: Exception) {
            unknown
        }
    }

}