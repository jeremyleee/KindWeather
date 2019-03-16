package com.tragicfruit.weatherapp.model

import androidx.annotation.StringRes
import com.tragicfruit.weatherapp.R

enum class ForecastType(@StringRes val label: Int = -1,
                        val units: String = "",
                        val minValue: Double = 0.0,
                        val maxValue: Double = 100.0) {

    Temp_high(R.string.forecast_type_temp_high, "°C", 0.0, 60.0),
    Temp_low(R.string.forecast_type_temp_low, "°C", -90.0, 30.0),
    Rain_intensity(R.string.forecast_type_rain_intensity, "mm/h", 0.0, 300.0),
    Rain_probability(R.string.forecast_type_rain_probability, "", 0.0, 1.0),
    Snow_intensity(R.string.forecast_type_snow_intensity, "mm/h", 0.0, 250.0),
    Snow_probability(R.string.forecast_type_snow_probability, "", 0.0, 1.0),
    Humidity(R.string.forecast_type_humidity, "", 0.0, 1.0),
    Wind_gust(R.string.forecast_type_wind_gust, "km/h", 0.0, 200.0),
    Uv_index(R.string.forecast_type_uv, "", 0.0, 10.0),

    Unknown;

    companion object {
        fun fromString(type: String?) = try {
            ForecastType.valueOf(type!!)
        } catch (e: Exception) {
            Unknown
        }
    }
}