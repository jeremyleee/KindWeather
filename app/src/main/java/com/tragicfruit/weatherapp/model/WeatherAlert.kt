package com.tragicfruit.weatherapp.model

import android.graphics.Color
import com.tragicfruit.weatherapp.utils.ColorHelper
import io.realm.Realm
import io.realm.RealmObject
import io.realm.kotlin.createObject

open class WeatherAlert : RealmObject() {

    private var id = ""
    var name = ""; private set
    var enabled = true; private set
    var color = Color.WHITE

    companion object {
        fun create(name: String, enabled: Boolean, realm: Realm): WeatherAlert {
            return realm.createObject<WeatherAlert>().apply {
                this.name = name
                this.enabled = enabled
                this.color = ColorHelper.getRandomColor()
            }
        }
    }

}