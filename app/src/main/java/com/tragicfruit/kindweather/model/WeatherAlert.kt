package com.tragicfruit.kindweather.model

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.tragicfruit.kindweather.R
import io.realm.Realm
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.kotlin.createObject
import io.realm.kotlin.where

open class WeatherAlert : RealmObject() {

    var id = 0; private set
    private var info = ""
    var priority = 0

    var enabled = true; private set
    var params = RealmList<WeatherAlertParam>(); private set

    fun getInfo() = Info.fromString(info)

    fun areParamsEdited() = params.any { it.isEdited() }

    fun shouldShowAlert(forecast: ForecastPeriod): Boolean {
        return params.all {
            forecast.satisfiesParam(it)
        }
    }

    companion object {

        fun create(id: Int, info: Info, realm: Realm): WeatherAlert {
            return realm.createObject<WeatherAlert>().apply {
                this.id = id
                this.info = info.name
                this.priority = id // TODO: determine a priority for alerts
            }
        }

        fun fromId(alertId: Int, realm: Realm = Realm.getDefaultInstance()) =
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

    enum class Info(@StringRes val title: Int = 0,
                    @StringRes val shortTitle: Int = 0,
                    @ColorRes val color: Int = 0,
                    @DrawableRes val image: Int = 0) {

        UMBRELLA(
            R.string.alert_umbrella,
            R.string.alert_umbrella_short,
            R.color.alert_umbrella,
            R.drawable.umbrella),

        JACKET(
            R.string.alert_jacket,
            R.string.alert_jacket_short,
            R.color.alert_jacket,
            R.drawable.warm_clothing),

        TSHIRT(
            R.string.alert_tshirt,
            R.string.alert_tshirt_short,
            R.color.alert_tshirt,
            R.drawable.tshirt_shorts),

        RAIN_JACKET(
            R.string.alert_rain_jacket,
            R.string.alert_rain_jacket_short,
            R.color.alert_rain_jacket,
            R.drawable.rain_jacket),

        SUNSCREEN(
            R.string.alert_sunscreen,
            R.string.alert_sunscreen_short,
            R.color.alert_sunscreen,
            R.drawable.sunscreen),

        THICK_JACKET(
            R.string.alert_thick_jacket,
            R.string.alert_thick_jacket_short,
            R.color.alert_thick_jacket,
            R.drawable.winter_clothing),

        UNKNOWN;

        companion object {
            fun fromString(info: String) = try {
                valueOf(info)
            } catch (e: Exception) {
                UNKNOWN
            }
        }

    }

}