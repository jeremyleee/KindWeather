package com.tragicfruit.kindweather.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "params")
data class WeatherAlertParam(
    @PrimaryKey val id: String,
    val alertId: String,
    val type: ForecastType,
    val rawDefaultLowerBound: Double?,
    val rawDefaultUpperBound: Double?,
    var rawLowerBound: Double?,
    var rawUpperBound: Double?
) {

    fun setLowerBound(value: Double?, usesImperialUnits: Boolean) {
        rawLowerBound = if (value != null) {
            type.toRawValue(value, usesImperialUnits)
        } else null
    }

    fun getLowerBound(usesImperialUnits: Boolean): Double? {
        return rawLowerBound?.let {
            type.fromRawValue(it, usesImperialUnits)
        }
    }

    fun setUpperBound(value: Double?, usesImperialUnits: Boolean) {
        rawUpperBound = if (value != null) {
            type.toRawValue(value, usesImperialUnits)
        } else null
    }

    fun getUpperBound(usesImperialUnits: Boolean): Double? {
        return rawUpperBound?.let {
            type.fromRawValue(it, usesImperialUnits)
        }
    }

    fun isEdited() = rawDefaultLowerBound != rawLowerBound || rawDefaultUpperBound != rawUpperBound
}
