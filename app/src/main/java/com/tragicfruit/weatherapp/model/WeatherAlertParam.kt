package com.tragicfruit.weatherapp.model

import io.realm.Realm
import io.realm.RealmObject
import io.realm.kotlin.createObject

open class WeatherAlertParam : RealmObject() {

    private var type = ""

    var defaultLowerBound: Double? = null; private set
    var defaultUpperBound: Double? = null; private set

    var lowerBound: Double? = null; private set
    var upperBound: Double? = null; private set

    fun getType(): ForecastType = ForecastType.fromString(type)

    companion object {

        fun create(type: ForecastType, defaultLowerBound: Double?, defaultUpperBound: Double?, realm: Realm): WeatherAlertParam {
            val param = realm.createObject<WeatherAlertParam>()
            param.type = type.name
            param.defaultLowerBound = defaultLowerBound
            param.defaultUpperBound = defaultUpperBound
            param.lowerBound = defaultLowerBound
            param.upperBound = defaultUpperBound
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