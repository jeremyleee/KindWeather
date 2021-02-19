package com.tragicfruit.kindweather.model

import com.tragicfruit.kindweather.utils.DisplayUtils
import io.realm.Realm
import io.realm.RealmObject
import io.realm.kotlin.createObject

open class ForecastData : RealmObject() {

    internal var type = ""
    var value = 0.0; internal set
        get() = ForecastType.fromString(type).fromRawValue(field)

    var fetchedTime: Long = 0; internal set
    var displayOnly = false;

    private fun getType() = ForecastType.fromString(type)
    fun getDisplayString() = DisplayUtils.getMeasurementString(value.toFloat(), getType().units, 0)
}