package com.tragicfruit.weatherapp.model

import io.realm.Realm
import io.realm.RealmObject
import io.realm.kotlin.createObject
import java.util.*

open class WeatherNotification : RealmObject() {

    private var id = ""
    var createdAt: Date? = null; private set
    var icon = ""; private set
    var highTemp = 0.0; private set
    var description = ""; private set

    companion object {
        fun create(icon: String, description: String, highTemp: Double, realm: Realm): WeatherNotification {
            return realm.createObject<WeatherNotification>().also {
                it.id = UUID.randomUUID().toString()
                it.createdAt = Date()
                it.icon = icon
                it.highTemp = highTemp
                it.description = description
            }
        }
    }

}