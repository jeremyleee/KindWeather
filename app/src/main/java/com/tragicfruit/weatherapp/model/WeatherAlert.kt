package com.tragicfruit.weatherapp.model

import android.graphics.Color
import com.tragicfruit.weatherapp.utils.ColorHelper
import io.realm.Realm
import io.realm.RealmObject
import io.realm.kotlin.createObject
import java.util.*

open class WeatherAlert : RealmObject() {

    private var id = ""
    var color = Color.WHITE; private set

    var name = ""; private set
    var enabled = true; private set
    var dateAdded: Date? = null; private set

    companion object {
        fun create(name: String, enabled: Boolean, realm: Realm): WeatherAlert {
            return realm.createObject<WeatherAlert>().apply {
                this.id = UUID.randomUUID().toString()
                this.name = name
                this.enabled = enabled
                this.color = ColorHelper.getRandomColor()
                this.dateAdded = Date()
            }
        }
    }

}