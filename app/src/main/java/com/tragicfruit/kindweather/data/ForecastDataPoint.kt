package com.tragicfruit.kindweather.data

import androidx.annotation.StringRes
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tragicfruit.kindweather.R
import com.tragicfruit.kindweather.utils.Converter

@Entity(tableName = "datapoints")
data class ForecastDataPoint(
    @PrimaryKey val id: String,
    val forecastId: String,
    val dataType: ForecastDataType,
    val rawValue: Double,
    val fetchedTime: Long
) {

    fun getValue(usesImperialUnits: Boolean): Double {
        return dataType.fromRawValue(rawValue, usesImperialUnits)
    }
}

enum class ForecastDataType(
    @StringRes val label: Int = -1,
    private val minValue: Double = 0.0,
    private val maxValue: Double = 100.0,
    private val metricUnits: String = "",
    private val imperialUnits: String = "",
    private val converter: Converter.Measurement = Converter.Default
) {

    TempHigh(
        label = R.string.forecast_type_temp_high,
        minValue = -20.0,
        maxValue = 60.0,
        metricUnits = "°C",
        imperialUnits = "°F",
        converter = Converter.Temperature
    ),

    TempLow(
        label = R.string.forecast_type_temp_low,
        minValue = -90.0,
        maxValue = 40.0,
        metricUnits = "°C",
        imperialUnits = "°F",
        converter = Converter.Temperature
    ),

    PrecipIntensity(
        label = R.string.forecast_type_precip_intensity,
        minValue = 0.0,
        maxValue = 100.0,
        metricUnits = "mm/h",
        imperialUnits = "in/h",
        converter = Converter.Precipitation
    ),

    PrecipProbability(
        label = R.string.forecast_type_precip_probability,
        minValue = 0.0,
        maxValue = 1.0,
        metricUnits = "%",
        imperialUnits = "%",
        converter = Converter.Probability
    ),

    Humidity(
        label = R.string.forecast_type_humidity,
        minValue = 0.0,
        maxValue = 1.0,
        metricUnits = "%",
        imperialUnits = "%",
        converter = Converter.Humidity
    ),

    WindGust(
        label = R.string.forecast_type_wind_gust,
        minValue = 0.0,
        maxValue = 33.0,
        metricUnits = "km/h",
        imperialUnits = "mph",
        converter = Converter.WindSpeed
    ),

    UVIndex(
        label = R.string.forecast_type_uv,
        minValue = 0.0,
        maxValue = 10.0
    );

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
}