package com.tragicfruit.weatherapp.model

import io.realm.Realm
import io.realm.RealmObject
import io.realm.kotlin.createObject

open class WeatherAlertParam : RealmObject() {

    private var type = ""

    var greaterThan: Double? = null; private set
    var greaterThanInclusive = false; private set

    var lessThan: Double? = null; private set
    var lessThanInclusive = false; private set

    fun getType() = ForecastType.fromString(type)

    companion object {

        fun create(type: ForecastType, realm: Realm): WeatherAlertParam {
            val param = realm.createObject<WeatherAlertParam>()
            param.type = type.name
            return param
        }

        fun addGreaterThanCondition(param: WeatherAlertParam, greaterThan: Double, isInclusive: Boolean, realm: Realm) {
            param.greaterThan = greaterThan
            param.greaterThanInclusive = isInclusive
        }

        fun addLessThanCondition(param: WeatherAlertParam, lessThan: Double, isInclusive: Boolean, realm: Realm) {
            param.lessThan = lessThan
            param.lessThanInclusive = isInclusive
        }

    }

}