package com.tragicfruit.weatherapp.model

import androidx.annotation.StringRes
import com.tragicfruit.weatherapp.R

enum class ForecastType(@StringRes val label: Int = -1, val units: String = "", val minValue: Double = 0.0, val maxValue: Double = 100.0) {
    Temp_high(R.string.forecast_type_temp_high, "°C", 0.0, 60.0),
    Temp_low(R.string.forecast_type_temp_low, "°C", -90.0, 30.0),
    Rainfall(R.string.forecast_type_rainfall, "mm/h", 0.0, 300.0),
    Rain_probability(R.string.forecast_type_rain_probability, "%", 0.0, 100.0),
    Snowfall(R.string.forecast_type_snowfall, "mm/h", 0.0, 250.0),
    Snow_probability(R.string.forecast_type_snow_probability, "%", 0.0, 100.0),
    Humidity(R.string.forecast_type_humidity, "%", 0.0, 100.0),
    Wind_speed(R.string.forecast_type_wind_speed, "m/s", 0.0, 120.0),
    Uv(R.string.forecast_type_uv, "", 0.0, 10.0),

    Unknown;

    companion object {
        fun fromString(type: String?) = try {
            ForecastType.valueOf(type!!)
        } catch (e: Exception) {
            Unknown
        }
    }
}