package com.tragicfruit.weatherapp.model

import android.graphics.Color
import io.realm.Realm
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import java.util.*

open class WeatherAlert : RealmObject() {

    var id = ""; private set
    var color = Color.WHITE; private set
    var drawableName = ""; private set
    var priority = 0; private set

    var name = ""; private set
    var enabled = true; private set
    var params = RealmList<WeatherAlertParam>(); private set

    var description = ""; private set

    fun areParamsEdited() = params.any { it.isEdited() }

    fun shouldShowAlert(forecast: ForecastPeriod): Boolean {
        return params.all {
            forecast.satisfiesParam(it)
        }
    }

    companion object {

        fun create(name: String,
                   description: String,
                   priority: Int,
                   color: Int,
                   drawableName: String,
                   realm: Realm): WeatherAlert {

            return realm.createObject<WeatherAlert>().apply {
                this.id = UUID.randomUUID().toString()
                this.name = name
                this.color = color
                this.drawableName = drawableName
                this.priority = priority
                this.description = description
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