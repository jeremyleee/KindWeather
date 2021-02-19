package com.tragicfruit.kindweather.model

import io.realm.RealmObject

open class WeatherAlertParam : RealmObject() {

    internal var type = ""

    internal var rawDefaultLowerBound: Double? = null
    internal var rawDefaultUpperBound: Double? = null
    internal var rawLowerBound: Double? = null
    internal var rawUpperBound: Double? = null

    fun getDefaultLowerBound(usesImperialUnits: Boolean): Double? {
        return rawDefaultLowerBound?.let {
            getType().fromRawValue(it, usesImperialUnits)
        }
    }

    fun getDefaultUpperBound(usesImperialUnits: Boolean): Double? {
        return rawDefaultUpperBound?.let {
            getType().fromRawValue(it, usesImperialUnits)
        }
    }

    fun setLowerBound(value: Double?, usesImperialUnits: Boolean) {
        rawLowerBound = if (value != null) {
            getType().toRawValue(value, usesImperialUnits)
        } else null
    }

    fun getLowerBound(usesImperialUnits: Boolean): Double? {
        return rawLowerBound?.let {
            getType().fromRawValue(it, usesImperialUnits)
        }
    }

    fun setUpperBound(value: Double?, usesImperialUnits: Boolean) {
        rawUpperBound = if (value != null) {
            getType().toRawValue(value, usesImperialUnits)
        } else null
    }

    fun getUpperBound(usesImperialUnits: Boolean): Double? {
        return rawUpperBound?.let {
            getType().fromRawValue(it, usesImperialUnits)
        }
    }

    fun getType(): ForecastType = ForecastType.fromString(type)

    fun isEdited() = rawDefaultLowerBound != rawLowerBound || rawDefaultUpperBound != rawUpperBound
}