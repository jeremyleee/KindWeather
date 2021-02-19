package com.tragicfruit.kindweather.model

import io.realm.RealmObject
import java.util.*

open class WeatherNotification : RealmObject() {
    var id = ""
    var createdAt: Date? = null
    var description = ""
    var forecast: ForecastPeriod? = null
    var color = 0
}