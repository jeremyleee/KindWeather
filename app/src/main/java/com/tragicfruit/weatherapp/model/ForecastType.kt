package com.tragicfruit.weatherapp.model

import androidx.annotation.StringRes
import com.tragicfruit.weatherapp.R

enum class ForecastType(@StringRes val label: Int = -1, val units: String = "", val minValue: Double = 0.0, val maxValue: Double = 100.0) {
    Temp(R.string.forecast_type_temp, "K", 180.0, 330.0),   // Kelvin
    Humidity(R.string.forecast_type_humidity, "%", 0.0, 100.0),                  // %
    Wind_speed(R.string.forecast_type_wind_speed, "m/s", 0.0, 120.0),            // m/s
    Clouds(R.string.forecast_type_clouds, "%", 0.0, 100.0),                  // %
    Rain(R.string.forecast_type_rain, "mm/hr", 0.0, 300.0),                      // mm/hr
    Snow(R.string.forecast_type_snow, "mm/hr", 0.0, 250.0),                      // mm/hr

    Unknown;

    companion object {
        fun fromString(type: String?) = try {
            ForecastType.valueOf(type!!)
        } catch (e: Exception) {
            Unknown
        }
    }
}