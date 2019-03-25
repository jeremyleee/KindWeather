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

    var id = ""; private set

    var latitude = 0.0; private set
    var longitude = 0.0; private set

    var time: Long = 0; private set
    var summary = ""; private set
    var icon = ""; private set
    var data = RealmList<ForecastData>(); private set

    var fetchedTime: Long = 0; private set

    var isDisplayed = false; private set

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

    companion object {

        fun fromResponse(responseData: ForecastResponse.Daily.DataPoint,
                         latitude: Double, longitude: Double, realm: Realm): ForecastPeriod {
            val forecastPeriod = realm.createObject<ForecastPeriod>()

            forecastPeriod.id = UUID.randomUUID().toString()

            forecastPeriod.latitude = latitude
            forecastPeriod.longitude = longitude

            forecastPeriod.time = responseData.time
            forecastPeriod.summary = responseData.summary ?: forecastPeriod.summary
            forecastPeriod.icon = responseData.icon ?: forecastPeriod.icon

            forecastPeriod.data.add(ForecastData.create(ForecastType.TEMP_HIGH, responseData.temperatureHigh, realm))
            forecastPeriod.data.add(ForecastData.create(ForecastType.TEMP_LOW, responseData.temperatureLow, realm))
            forecastPeriod.data.add(ForecastData.create(ForecastType.PRECIP_INTENSITY, responseData.precipIntensity, realm))
            forecastPeriod.data.add(ForecastData.create(ForecastType.PRECIP_PROBABILITY, responseData.precipProbability, realm))
            forecastPeriod.data.add(ForecastData.create(ForecastType.HUMIDITY, responseData.humidity, realm))
            forecastPeriod.data.add(ForecastData.create(ForecastType.WIND_GUST, responseData.windGust, realm))
            forecastPeriod.data.add(ForecastData.create(ForecastType.UV_INDEX, responseData.uvIndex?.toDouble(), realm))

            forecastPeriod.fetchedTime = System.currentTimeMillis()

            return forecastPeriod
        }

        fun setDisplayed(forecast: ForecastPeriod, displayed: Boolean, realm: Realm) {
            forecast.isDisplayed = displayed
            forecast.data.forEach { data ->
                ForecastData.setDisplayed(data, displayed, realm)
            }
        }

    }

}