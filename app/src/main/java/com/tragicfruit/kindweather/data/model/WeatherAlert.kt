package com.tragicfruit.kindweather.data.model

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.tragicfruit.kindweather.R
import io.realm.RealmList
import io.realm.RealmObject

open class WeatherAlert : RealmObject() {

    internal var id = 0
    internal var info = ""
    var priority = 0

    var enabled = true
    var params = RealmList<WeatherAlertParam>(); private set

    fun getInfo() = Info.fromString(info)

    fun areParamsEdited() = params.any { it.isEdited() }

    fun shouldShowAlert(forecast: ForecastPeriod, usesImperialUnits: Boolean): Boolean {
        return params.all {
            forecast.satisfiesParam(it, usesImperialUnits)
        }
    }

    enum class Info(
        @StringRes val title: Int = 0,
        @StringRes val shortTitle: Int = 0,
        @ColorRes val color: Int = 0,
        @DrawableRes val image: Int = 0
    ) {

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

        SUNSCREEN(
            R.string.alert_sunscreen,
            R.string.alert_sunscreen_short,
            R.color.alert_sunscreen,
            R.drawable.sunscreen),

        RAIN_JACKET(
            R.string.alert_rain_jacket,
            R.string.alert_rain_jacket_short,
            R.color.alert_rain_jacket,
            R.drawable.rain_jacket),

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