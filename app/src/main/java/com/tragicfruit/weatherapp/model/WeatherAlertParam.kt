package com.tragicfruit.weatherapp.model

import io.realm.Realm
import io.realm.RealmObject
import io.realm.kotlin.createObject

open class WeatherAlertParam : RealmObject() {

    private var type = ""

    var defaultLowerBound: Double? = null; private set
        get() {
            val value = field ?: return null
            return getType().fromRawValue(value)
        }

    var defaultUpperBound: Double? = null; private set
        get() {
            val value = field ?: return null
            return getType().fromRawValue(value)
        }

    var lowerBound: Double? = null
        private set(value) {
            field = if (value != null) {
                getType().toRawValue(value)
            } else null
        }
        get() {
            val value = field ?: return null
            return getType().fromRawValue(value)
        }

    var upperBound: Double? = null
        private set(value) {
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

    companion object {

        fun create(type: ForecastType, defaultLowerBoundRaw: Double?, defaultUpperBoundRaw: Double?, realm: Realm): WeatherAlertParam {
            val param = realm.createObject<WeatherAlertParam>()
            param.type = type.name
            param.defaultLowerBound = defaultLowerBoundRaw
            param.defaultUpperBound = defaultUpperBoundRaw
            param.lowerBound = param.defaultLowerBound
            param.upperBound = param.defaultUpperBound
            return param
        }

        fun setLowerBound(param: WeatherAlertParam, lowerBound: Double?, realm: Realm) {
            param.lowerBound = lowerBound
        }

        fun setUpperBound(param: WeatherAlertParam, upperBound: Double?, realm: Realm) {
            param.upperBound = upperBound
        }

        fun resetToDefault(param: WeatherAlertParam, realm: Realm) {
            param.lowerBound = param.defaultLowerBound
            param.upperBound = param.defaultUpperBound
        }

    }

}