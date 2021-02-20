package com.tragicfruit.kindweather.data.model

import io.realm.RealmObject

open class ForecastData : RealmObject() {

    var type = ""
    var rawValue = 0.0
    var fetchedTime: Long = 0

    fun getValue(usesImperialUnits: Boolean): Double {
        return enumValueOf<ForecastType>(type).fromRawValue(rawValue, usesImperialUnits)
    }
}
