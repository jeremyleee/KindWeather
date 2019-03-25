package com.tragicfruit.kindweather.model

import com.tragicfruit.kindweather.utils.DisplayUtils
import io.realm.Realm
import io.realm.RealmObject
import io.realm.kotlin.createObject

open class ForecastData : RealmObject() {

    private var type = ""
    var value = 0.0; private set
        get() = ForecastType.fromString(type).fromRawValue(field)

    var fetchedTime: Long = 0; private set
    var isDisplayed = false; private set

    fun getType() = ForecastType.fromString(type)
    fun getDisplayString() = DisplayUtils.getMeasurementString(value.toFloat(), getType().units, 0)

    companion object {
        fun create(type: ForecastType, rawValue: Double?, realm: Realm): ForecastData? {
            val value = rawValue ?: return null

            val data = realm.createObject<ForecastData>()
            data.type = type.name
            data.value = value
            data.fetchedTime = System.currentTimeMillis()
            return data
        }

        fun setDisplayed(data: ForecastData, displayed: Boolean, realm: Realm) {
            data.isDisplayed = displayed
        }
    }

}