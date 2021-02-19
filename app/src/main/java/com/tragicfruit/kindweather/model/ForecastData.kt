package com.tragicfruit.kindweather.model

import com.tragicfruit.kindweather.utils.DisplayUtils
import io.realm.Realm
import io.realm.RealmObject
import io.realm.kotlin.createObject

open class ForecastData : RealmObject() {

    internal var type = ""
    internal var rawValue = 0.0
    var fetchedTime: Long = 0; internal set
    var displayOnly = false

    fun getValue(usesImperialUnits: Boolean): Double {
        return ForecastType.fromString(type).fromRawValue(rawValue, usesImperialUnits)
    }

    private fun getType() = ForecastType.fromString(type)

    fun getDisplayString(usesImperialUnits: Boolean): String {
        val convertedValue = getValue(usesImperialUnits)
        return DisplayUtils.getMeasurementString(
            value = convertedValue.toFloat(),
            units = getType().getUnits(usesImperialUnits),
            decimalPlaces = 0
        )
    }
}