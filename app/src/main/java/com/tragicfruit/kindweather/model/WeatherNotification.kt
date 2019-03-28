package com.tragicfruit.kindweather.model

import androidx.annotation.ColorInt
import io.realm.Realm
import io.realm.RealmObject
import io.realm.kotlin.createObject
import java.util.*

open class WeatherNotification : RealmObject() {

    private var id = ""
    var createdAt: Date? = null; private set
    var description = ""; private set
    var forecast: ForecastPeriod? = null; private set
    var color = 0; private set

    companion object {
        fun create(description: String, forecast: ForecastPeriod, @ColorInt color: Int, realm: Realm): WeatherNotification {
            return realm.createObject<WeatherNotification>().also {
                it.id = UUID.randomUUID().toString()
                it.createdAt = Date()
                it.description = description
                it.forecast = forecast
                it.color = color
            }
        }
    }

}