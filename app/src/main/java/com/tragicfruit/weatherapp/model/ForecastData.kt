package com.tragicfruit.weatherapp.model

import io.realm.Realm
import io.realm.RealmObject
import io.realm.kotlin.createObject

open class ForecastData : RealmObject() {

    private var type = ""
    var value = 0.0; private set

    var fetchedTime: Long = 0; private set

    companion object {
        fun create(type: ForecastType, value: Double?, realm: Realm): ForecastData? {
            val value = value ?: return null

            val data = realm.createObject<ForecastData>()
            data.type = type.name
            data.value = value
            data.fetchedTime = System.currentTimeMillis()
            return data
        }
    }

}