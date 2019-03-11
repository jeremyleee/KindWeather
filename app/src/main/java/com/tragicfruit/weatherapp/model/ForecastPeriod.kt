package com.tragicfruit.weatherapp.model

import com.tragicfruit.weatherapp.controllers.ForecastResponse
import io.realm.Realm
import io.realm.RealmObject
import io.realm.kotlin.createObject
import io.realm.kotlin.where

/**
 * Forecast for a 3 hour period of time
 */
open class ForecastPeriod : RealmObject() {

    // Identifiers
    private var cityId = 0
    var timeOfData: Long = 0; private set

    private var latitude = 0.0
    private var longitude = 0.0

    private var weatherConditionId = 0

    private var tempMax = 0.0
    private var tempMin = 0.0
    private var pressure = 0.0
    private var humidity = 0
    private var windSpeed = 0.0
    private var clouds = 0
    private var rain3h = 0.0
    private var snow3h = 0.0

    companion object {

        fun fromCityId(cityId: Int, timeOfData: Long, realm: Realm): ForecastPeriod {
            val existing = realm.where<ForecastPeriod>()
                .equalTo("cityId", cityId)
                .equalTo("timeOfData", timeOfData)
                .findFirst()

            return existing ?: realm.createObject<ForecastPeriod>().apply {
                this.cityId = cityId
                this.timeOfData = timeOfData
            }
        }

        fun fromResponse(responseItem: ForecastResponse.Item, responseCity: ForecastResponse.City, realm: Realm): ForecastPeriod {
            val forecastPeriod = fromCityId(responseCity.id, responseItem.dt, realm)

            forecastPeriod.latitude = responseCity.coord.lat
            forecastPeriod.longitude = responseCity.coord.lon

            forecastPeriod.weatherConditionId = responseItem.weather?.firstOrNull()?.id ?: forecastPeriod.weatherConditionId

            forecastPeriod.tempMax = responseItem.main?.temp_max ?: forecastPeriod.tempMax
            forecastPeriod.tempMin = responseItem.main?.temp_min ?: forecastPeriod.tempMin
            forecastPeriod.pressure = responseItem.main?.pressure ?: forecastPeriod.pressure
            forecastPeriod.humidity = responseItem.main?.humidity ?: forecastPeriod.humidity

            forecastPeriod.windSpeed = responseItem.wind?.speed ?: forecastPeriod.windSpeed
            forecastPeriod.clouds = responseItem.clouds?.all ?: forecastPeriod.clouds
            forecastPeriod.rain3h = responseItem.rain?.threeHour ?: forecastPeriod.rain3h
            forecastPeriod.snow3h = responseItem.snow?.threeHour ?: forecastPeriod.snow3h

            return forecastPeriod
        }

    }

}