package com.tragicfruit.kindweather.model

import io.realm.RealmList
import io.realm.RealmObject
import timber.log.Timber

/**
 * Forecast for a day
 */
open class ForecastPeriod : RealmObject() {

    var id = "";

    var latitude = 0.0;
    var longitude = 0.0;

    var time: Long = 0;
    var summary = "";
    var icon = "";
    var data = RealmList<ForecastData>(); private set

    var fetchedTime: Long = 0;

    var displayOnly = false;

    fun satisfiesParam(param: WeatherAlertParam, usesImperialUnits: Boolean): Boolean {
        val lowerBound = param.getLowerBound(usesImperialUnits)
        val upperBound = param.getUpperBound(usesImperialUnits)

        val data = getDataForType(param.getType())

        // If data doesn't exist, treat it as zero
        val observedValue = data?.getValue(usesImperialUnits) ?: 0.0

        val satisfiesLowerBound = lowerBound == null || observedValue >= lowerBound
        val satisfiesUpperBound = upperBound == null || observedValue <= upperBound

        Timber.d("param:${param.getType().name}; observed:$observedValue; lowerbound:$lowerBound; upperbound:$upperBound; satisfies:${satisfiesLowerBound && satisfiesUpperBound}")

        return satisfiesLowerBound && satisfiesUpperBound
    }

    fun getDataForType(type: ForecastType) =
        data.where().equalTo("type", type.name).findFirst()
}