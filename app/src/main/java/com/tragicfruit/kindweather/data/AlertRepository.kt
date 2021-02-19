package com.tragicfruit.kindweather.data

import com.tragicfruit.kindweather.model.ForecastType
import com.tragicfruit.kindweather.model.WeatherAlert
import com.tragicfruit.kindweather.model.WeatherAlertParam
import io.realm.Realm
import io.realm.RealmResults
import io.realm.Sort
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import javax.inject.Inject

class AlertRepository @Inject constructor() {

    fun createAlert(id: Int, info: WeatherAlert.Info, realm: Realm): WeatherAlert {
        return realm.createObject<WeatherAlert>().apply {
            this.id = id
            this.info = info.name
            this.priority = id // TODO: determine a priority for alerts
        }
    }

    fun createParam(
        alert: WeatherAlert,
        type: ForecastType,
        defaultLowerBoundRaw: Double?,
        defaultUpperBoundRaw: Double?,
        realm: Realm
    ): WeatherAlertParam {
        return realm.createObject<WeatherAlertParam>().apply {
            this.type = type.name
            this.defaultLowerBound = defaultLowerBoundRaw
            this.defaultUpperBound = defaultUpperBoundRaw
            this.lowerBound = this.defaultLowerBound
            this.upperBound = this.defaultUpperBound
        }.also {
            alert.params.add(it)
        }
    }

    fun findAlert(id: Int) =
        Realm.getDefaultInstance().where<WeatherAlert>().equalTo("id", id).findFirst()

    fun getAllAlerts(): RealmResults<WeatherAlert> {
        return Realm.getDefaultInstance()
            .where<WeatherAlert>()
            .sort("enabled", Sort.DESCENDING, "priority", Sort.ASCENDING)
            .findAll()
    }

    fun setAlertEnabled(alert: WeatherAlert, enabled: Boolean) {
        Realm.getDefaultInstance().executeTransaction {
            alert.enabled = enabled
        }
    }

    fun getAlertCount(): Long {
        return Realm.getDefaultInstance().where<WeatherAlert>().count()
    }

    fun setParamLowerBound(param: WeatherAlertParam, lowerBound: Double?) {
        Realm.getDefaultInstance().executeTransaction {
            param.lowerBound = lowerBound
        }
    }

    fun setParamUpperBound(param: WeatherAlertParam, upperBound: Double?) {
        Realm.getDefaultInstance().executeTransaction {
            param.upperBound = upperBound
        }
    }

    fun resetParamsToDefault(param: WeatherAlertParam) {
        Realm.getDefaultInstance().executeTransaction {
            param.lowerBound = param.defaultLowerBound
            param.upperBound = param.defaultUpperBound
        }
    }
}