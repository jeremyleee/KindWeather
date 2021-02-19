package com.tragicfruit.kindweather.model

import io.realm.Realm
import io.realm.RealmObject
import io.realm.kotlin.createObject

open class WeatherAlertParam : RealmObject() {

    internal var type = ""

    var defaultLowerBound: Double? = null; internal set
        get() {
            val value = field ?: return null
            return getType().fromRawValue(value)
        }

    var defaultUpperBound: Double? = null; internal set
        get() {
            val value = field ?: return null
            return getType().fromRawValue(value)
        }

    var lowerBound: Double? = null
        internal set(value) {
            field = if (value != null) {
                getType().toRawValue(value)
            } else null
        }
        get() {
            val value = field ?: return null
            return getType().fromRawValue(value)
        }

    var upperBound: Double? = null
        internal set(value) {
            field = if (value != null) {
                getType().toRawValue(value)
            } else null
        }
        get() {
            val value = field ?: return null
            return getType().fromRawValue(value)
        }

    fun getType(): ForecastType = ForecastType.fromString(type)

    fun isEdited() = defaultLowerBound != lowerBound || defaultUpperBound != upperBound
}