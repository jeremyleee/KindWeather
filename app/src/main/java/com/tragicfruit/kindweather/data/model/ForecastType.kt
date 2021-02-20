package com.tragicfruit.kindweather.data.model

import androidx.annotation.StringRes
import com.tragicfruit.kindweather.R
import com.tragicfruit.kindweather.utils.Converter

enum class ForecastType(
    @StringRes val label: Int = -1,
    private val minValue: Double = 0.0,
    private val maxValue: Double = 100.0,
    private val metricUnits: String = "",
    private val imperialUnits: String = "",
    private val converter: Converter.Measurement = Converter.Default
) {

    TEMP_HIGH(
        label = R.string.forecast_type_temp_high,
        minValue = -20.0,
        maxValue = 60.0,
        metricUnits = "°C",
        imperialUnits = "°F",
        converter = Converter.Temperature
    ),

    TEMP_LOW(
        label = R.string.forecast_type_temp_low,
        minValue = -90.0,
        maxValue = 40.0,
        metricUnits = "°C",
        imperialUnits = "°F",
        converter = Converter.Temperature
    ),

    PRECIP_INTENSITY(
        label = R.string.forecast_type_precip_intensity,
        minValue = 0.0,
        maxValue = 100.0,
        metricUnits = "mm/h",
        imperialUnits = "in/h",
        converter = Converter.Precipitation
    ),

    PRECIP_PROBABILITY(
        label = R.string.forecast_type_precip_probability,
        minValue = 0.0,
        maxValue = 1.0,
        metricUnits = "%",
        imperialUnits = "%",
        converter = Converter.Probability
    ),

    HUMIDITY(
        label = R.string.forecast_type_humidity,
        minValue = 0.0,
        maxValue = 1.0,
        metricUnits = "%",
        imperialUnits = "%",
        converter = Converter.Humidity
    ),

    WIND_GUST(
        label = R.string.forecast_type_wind_gust,
        minValue = 0.0,
        maxValue = 33.0,
        metricUnits = "km/h",
        imperialUnits = "mph",
        converter = Converter.WindSpeed
    ),

    UV_INDEX(
        label = R.string.forecast_type_uv,
        minValue = 0.0,
        maxValue = 10.0
    ),

    UNKNOWN;

    fun getMinValue(usesImperialUnits: Boolean) = fromRawValue(minValue, usesImperialUnits)

    fun getMaxValue(usesImperialUnits: Boolean) = fromRawValue(maxValue, usesImperialUnits)

    fun getUnits(usesImperialUnits: Boolean): String {
        return if (usesImperialUnits) {
            imperialUnits
        } else {
            metricUnits
        }
    }

    fun toRawValue(value: Double, usesImperialUnits: Boolean): Double {
        return if (usesImperialUnits) {
            converter.fromImperial(value)
        } else {
            converter.fromMetric(value)
        }
    }

    fun fromRawValue(value: Double, usesImperialUnits: Boolean): Double {
        return if (usesImperialUnits) {
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
    }
}