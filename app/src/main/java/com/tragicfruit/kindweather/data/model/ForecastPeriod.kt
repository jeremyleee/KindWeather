package com.tragicfruit.kindweather.data.model

import androidx.annotation.DrawableRes
import com.tragicfruit.kindweather.R
import io.realm.RealmList
import io.realm.RealmObject
import timber.log.Timber

/**
 * Forecast for a day
 */
open class ForecastPeriod : RealmObject() {

    var id = ""

    var latitude = 0.0
    var longitude = 0.0

    var time: Long = 0
    var summary = ""
    var icon = ""
    var data = RealmList<ForecastData>()
        private set

    var fetchedTime: Long = 0

    fun satisfiesParam(param: WeatherAlertParam, usesImperialUnits: Boolean): Boolean {
        val lowerBound = param.getLowerBound(usesImperialUnits)
        val upperBound = param.getUpperBound(usesImperialUnits)

        val data = getDataForType(param.type)

        // If data doesn't exist, treat it as zero
        val observedValue = data?.getValue(usesImperialUnits) ?: 0.0

        val satisfiesLowerBound = lowerBound == null || observedValue >= lowerBound
        val satisfiesUpperBound = upperBound == null || observedValue <= upperBound

        Timber.d("param:${param.type.name}; observed:$observedValue; lowerbound:$lowerBound; upperbound:$upperBound; satisfies:${satisfiesLowerBound && satisfiesUpperBound}")

        return satisfiesLowerBound && satisfiesUpperBound
    }

    fun getDataForType(type: ForecastType) =
        data.where().equalTo("type", type.name).findFirst()
}

enum class ForecastIcon(@DrawableRes val iconRes: Int = 0) {
    clearday(R.drawable.ic_sunny),
    clearnight(R.drawable.ic_sunny),
    rain(R.drawable.ic_rain),
    snow(R.drawable.ic_snow),
    sleet(R.drawable.ic_snow),
    wind(R.drawable.ic_windy),
    fog(R.drawable.ic_fog),
    cloudy(R.drawable.ic_cloudy),
    partlycloudyday(R.drawable.ic_partly_cloudy),
    partlycloudynight(R.drawable.ic_partly_cloudy),

    unknown;

    companion object {
        fun fromString(type: String?) = try {
            ForecastIcon.valueOf(type!!.replace("-", ""))
        } catch (e: Exception) {
            unknown
        }
    }
}