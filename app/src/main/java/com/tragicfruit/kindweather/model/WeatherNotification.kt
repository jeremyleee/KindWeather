package com.tragicfruit.kindweather.model

import com.tragicfruit.kindweather.utils.DisplayUtils
import io.realm.RealmObject
import java.util.*

open class WeatherNotification : RealmObject() {
    var id = ""
    var createdAt = Date()
    var description = ""
    var color = 0

    var forecastIcon = ""
    var rawTempHigh = 0.0
    var rawTempLow = 0.0
    var rawPrecipProbability = 0.0

    var latitude = 0.0
    var longitude = 0.0

    fun getTempHighString(useImperial: Boolean): String {
        return getDisplayString(rawTempHigh, ForecastType.TEMP_HIGH, useImperial)
    }

    fun getTempLowString(useImperial: Boolean): String {
        return getDisplayString(rawTempLow, ForecastType.TEMP_LOW, useImperial)
    }

    fun getPrecipProbabilityString(useImperial: Boolean): String {
        return getDisplayString(rawPrecipProbability, ForecastType.PRECIP_PROBABILITY, useImperial)
    }

    private fun getDisplayString(rawValue: Double, type: ForecastType, useImperial: Boolean): String {
        val convertedValue = type.fromRawValue(rawValue, useImperial)
        val unitsLabel = type.getUnits(useImperial)

        return DisplayUtils.getMeasurementString(
            value = convertedValue.toFloat(),
            units = unitsLabel,
            decimalPlaces = 0
        )
    }
}