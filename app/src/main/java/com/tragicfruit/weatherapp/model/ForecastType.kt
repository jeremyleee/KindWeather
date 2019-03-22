package com.tragicfruit.weatherapp.model

import androidx.annotation.StringRes
import com.tragicfruit.weatherapp.R
import com.tragicfruit.weatherapp.utils.Converter
import com.tragicfruit.weatherapp.utils.SharedPrefsHelper

enum class ForecastType(@StringRes val label: Int = -1,
                        minValue: Double = 0.0,
                        maxValue: Double = 100.0,
                        private val metricUnits: String = "",
                        private val imperialUnits: String = "",
                        private val converter: Converter.Measurement = Converter.Default) {

    TEMP_HIGH(R.string.forecast_type_temp_high, 0.0, 60.0, "°C", "°F", Converter.Temperature),
    TEMP_LOW(R.string.forecast_type_temp_low, -90.0, 30.0, "°C", "°F", Converter.Temperature),
    RAIN_INTENSITY(R.string.forecast_type_rain_intensity, 0.0, 100.0, "mm/h", "in/h", Converter.Precipitation),
    RAIN_PROBABILITY(R.string.forecast_type_rain_probability, 0.0, 1.0, "%", "%", Converter.Probability),
    SNOW_INTENSITY(R.string.forecast_type_snow_intensity, 0.0, 100.0, "mm/h", "in/h", Converter.Precipitation),
    SNOW_PROBABILITY(R.string.forecast_type_snow_probability, 0.0, 1.0, "%", "%", Converter.Probability),
    HUMIDITY(R.string.forecast_type_humidity, 0.0, 1.0, "%", "%", Converter.Humidity),
    WIND_GUST(R.string.forecast_type_wind_gust, 0.0, 33.0, "km/h", "mph", Converter.WindSpeed),
    UV_INDEX(R.string.forecast_type_uv, 0.0, 10.0),

    UNKNOWN;

    val minValue = minValue
        get() = fromRawValue(field)

    val maxValue = maxValue
        get() = fromRawValue(field)

    val units: String
        get() = getUnits(this)

    fun toRawValue(value: Double): Double {
        return if (SharedPrefsHelper.usesImperialUnits()) {
            converter.fromImperial(value)
        } else {
            converter.fromMetric(value)
        }
    }

    fun fromRawValue(value: Double): Double {
        return if (SharedPrefsHelper.usesImperialUnits()) {
            converter.toImperial(value)
        } else {
            converter.toMetric(value)
        }
    }

    companion object {
        fun fromString(type: String) = try {
            ForecastType.valueOf(type)
        } catch (e: Exception) {
            UNKNOWN
        }

        fun getUnits(type: ForecastType): String {
            return if (SharedPrefsHelper.usesImperialUnits()) {
                type.imperialUnits
            } else {
                type.metricUnits
            }
        }
    }
}