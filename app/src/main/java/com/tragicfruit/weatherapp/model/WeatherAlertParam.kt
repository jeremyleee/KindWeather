package com.tragicfruit.weatherapp.model

import io.realm.RealmObject

open class WeatherAlertParam : RealmObject() {

    private var type = ""

    var greaterThan: Double? = null; private set
    var greaterThanInclusive = false; private set

    var lessThan: Double? = null; private set
    var lessThanInclusive = false; private set

    fun getType() = ForecastInfo.Type.fromString(type)

}