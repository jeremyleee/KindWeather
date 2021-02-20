package com.tragicfruit.kindweather.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tragicfruit.kindweather.utils.DisplayUtils
import java.util.*

@Entity(tableName = "notifications")
data class WeatherNotification(
    @PrimaryKey val id: String,
    val createdAt: Date,
    val description: String,
    val color: Int,
    val forecastIconName: String,
    val rawTempHigh: Double?,
    val rawTempLow: Double?,
    val rawPrecipProbability: Double?,
    val latitude: Double,
    val longitude: Double
) {
    fun getTempHighString(useImperial: Boolean): String {
        return getDisplayString(rawTempHigh, ForecastType.TempHigh, useImperial)
    }

    fun getTempLowString(useImperial: Boolean): String {
        return getDisplayString(rawTempLow, ForecastType.TempLow, useImperial)
    }

    fun getPrecipProbabilityString(useImperial: Boolean): String {
        return getDisplayString(rawPrecipProbability, ForecastType.PrecipProbability, useImperial)
    }

    private fun getDisplayString(rawValue: Double?, type: ForecastType, useImperial: Boolean): String {
        rawValue ?: return ""

        val convertedValue = type.fromRawValue(rawValue, useImperial)
        val unitsLabel = type.getUnits(useImperial)

        return DisplayUtils.getMeasurementString(
            value = convertedValue.toFloat(),
            units = unitsLabel,
            decimalPlaces = 0
        )
    }
}
