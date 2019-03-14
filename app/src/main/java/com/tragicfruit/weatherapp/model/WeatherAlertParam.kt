package com.tragicfruit.weatherapp.model

import io.realm.Realm
import io.realm.RealmObject
import io.realm.kotlin.createObject

open class WeatherAlertParam : RealmObject() {

    private var type = ""

    var lowerBound: Double? = null; private set // Inclusive
    var upperBound: Double? = null; private set // Inclusive

    fun getType(): ForecastType = ForecastType.fromString(type)

    companion object {

        fun create(type: ForecastType, lowerBound: Double?, upperBound: Double?, realm: Realm): WeatherAlertParam {
            val param = realm.createObject<WeatherAlertParam>()
            param.type = type.name
            param.lowerBound = lowerBound
            param.upperBound = upperBound
            return param
        }

        fun setLowerBound(param: WeatherAlertParam, lowerBound: Double?, realm: Realm) {
            param.lowerBound = lowerBound
        }

        fun setUpperBound(param: WeatherAlertParam, upperBound: Double?, realm: Realm) {
            param.upperBound = upperBound
        }

    }

}