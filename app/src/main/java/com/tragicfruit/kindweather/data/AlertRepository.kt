package com.tragicfruit.kindweather.data

import com.tragicfruit.kindweather.data.model.ForecastType
import com.tragicfruit.kindweather.data.model.WeatherAlert
import com.tragicfruit.kindweather.data.model.WeatherAlertParam
import com.tragicfruit.kindweather.utils.SharedPrefsHelper
import io.realm.Realm
import io.realm.RealmResults
import io.realm.Sort
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import javax.inject.Inject

class AlertRepository @Inject constructor(
    private val sharedPrefsHelper: SharedPrefsHelper
) {

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
            this.rawDefaultLowerBound = defaultLowerBoundRaw
            this.rawDefaultUpperBound = defaultUpperBoundRaw
            this.rawLowerBound = this.rawDefaultLowerBound
            this.rawUpperBound = this.rawDefaultUpperBound
        }.also {
            alert.params.add(it)
        }
    }

    fun findAlert(id: Int): WeatherAlert? {
        return Realm.getDefaultInstance()
            .where<WeatherAlert>()
            .equalTo("id", id)
            .findFirst()
    }

    fun getAllAlerts(): RealmResults<WeatherAlert> {
        return Realm.getDefaultInstance()
            .where<WeatherAlert>()
            .sort("enabled", Sort.DESCENDING, "priority", Sort.ASCENDING)
            .findAll()
    }

    fun getEnabledAlerts(): RealmResults<WeatherAlert> {
        return Realm.getDefaultInstance().where<WeatherAlert>()
            .equalTo("enabled", true)
            .sort("priority", Sort.ASCENDING)
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
            param.setLowerBound(lowerBound, sharedPrefsHelper.usesImperialUnits())
        }
    }

    fun setParamUpperBound(param: WeatherAlertParam, upperBound: Double?) {
        Realm.getDefaultInstance().executeTransaction {
            param.setUpperBound(upperBound, sharedPrefsHelper.usesImperialUnits())
        }
    }

    fun resetParamsToDefault(alert: WeatherAlert) {
        Realm.getDefaultInstance().executeTransaction {
            alert.params.forEach {
                it.rawLowerBound = it.rawDefaultLowerBound
                it.rawUpperBound = it.rawDefaultUpperBound
            }
        }
    }
}