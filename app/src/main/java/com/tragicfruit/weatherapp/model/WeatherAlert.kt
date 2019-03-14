package com.tragicfruit.weatherapp.model

import android.graphics.Color
import com.tragicfruit.weatherapp.utils.ColorHelper
import io.realm.Realm
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import java.util.*

open class WeatherAlert : RealmObject() {

    var id = ""; private set
    var color = Color.WHITE; private set

    var name = ""; private set
    var enabled = true; private set
    var dateAdded: Date? = null; private set
    var params = RealmList<WeatherAlertParam>(); private set

    fun areParamsEdited() = params.any { it.isEdited() }

    companion object {

        fun create(name: String, realm: Realm): WeatherAlert {
            return realm.createObject<WeatherAlert>().apply {
                this.id = UUID.randomUUID().toString()
                this.name = name
                this.color = ColorHelper.getRandomColor()
                this.dateAdded = Date()
            }
        }

        fun fromId(alertId: String, realm: Realm = Realm.getDefaultInstance()) =
            realm.where<WeatherAlert>().equalTo("id", alertId).findFirst()

        fun setEnabled(alert: WeatherAlert, enabled: Boolean, realm: Realm) {
            alert.enabled = enabled
        }

        fun addParam(alert: WeatherAlert, param: WeatherAlertParam, realm: Realm) {
            alert.params.add(param)
        }

        fun addParam(alert: WeatherAlert, type: ForecastType, defaultLowerBound: Double?, defaultUpperBound: Double?, realm: Realm) {
            val param = WeatherAlertParam.create(type, defaultLowerBound, defaultUpperBound, realm)
            addParam(alert, param, realm)
        }

    }

}