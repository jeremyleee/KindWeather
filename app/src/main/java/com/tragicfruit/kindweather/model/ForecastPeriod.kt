package com.tragicfruit.kindweather.model

import com.tragicfruit.kindweather.controllers.ForecastResponse
import io.realm.Realm
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.kotlin.createObject
import timber.log.Timber
import java.util.*

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

    fun satisfiesParam(param: WeatherAlertParam): Boolean {
        val lowerBound = param.lowerBound
        val upperBound = param.upperBound

        val data = getDataForType(param.getType())

        // If data doesn't exist, treat it as zero
        val observedValue = data?.value ?: 0.0

        val satisfiesLowerBound = lowerBound == null || observedValue >= lowerBound
        val satisfiesUpperBound = upperBound == null || observedValue <= upperBound

        Timber.d("param:${param.getType().name}; observed:$observedValue; lowerbound:$lowerBound; upperbound:$upperBound; satisfies:${satisfiesLowerBound && satisfiesUpperBound}")

        return satisfiesLowerBound && satisfiesUpperBound
    }

    fun getDataForType(type: ForecastType) =
        data.where().equalTo("type", type.name).findFirst()
}