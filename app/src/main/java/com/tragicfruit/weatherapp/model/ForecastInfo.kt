package com.tragicfruit.weatherapp.model

import androidx.annotation.StringRes
import com.tragicfruit.weatherapp.R
import io.realm.RealmObject

open class ForecastInfo : RealmObject() {
    enum class Type(@StringRes val label: Int) {
        Temp_max(R.string.forecast_type_max_temp),      // Kelvin
        Temp_min(R.string.forecast_type_min_temp),      // Kelvin
        Pressure(R.string.forecast_type_pressure),      // hPa
        Humidity(R.string.forecast_type_humidity),      // %
        Wind_speed(R.string.forecast_type_wind_speed),  // m/s
        Clouds(R.string.forecast_type_clouds),          // %
        Rain(R.string.forecast_type_rain),              // mm/hr
        Snow(R.string.forecast_type_snow);              // mm/hr

        companion object {
            fun fromString(type: String?) = try {
                Type.valueOf(type!!)
            } catch (e: Exception) {
                null
            }
        }
    }

    private var type = ""
    var value = 0.0; private set

    fun getType() = Type.fromString(type)

}