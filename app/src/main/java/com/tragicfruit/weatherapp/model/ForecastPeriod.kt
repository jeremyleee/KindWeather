package com.tragicfruit.weatherapp.model

import com.tragicfruit.weatherapp.controllers.ForecastResponse
import io.realm.Realm
import io.realm.RealmObject
import io.realm.kotlin.createObject
import java.util.*

/**
 * Forecast for a day
 */
open class ForecastPeriod : RealmObject() {

    var latitude = 0.0; private set
    var longitude = 0.0; private set

    var time: Long = 0; private set
    var summary: String? = null; private set
    var icon: String? = null; private set
    var precipIntensity: Double? = null; private set    // mm/h
    var precipProbability: Double? = null; private set  // mm/h
    var precipType: String? = null; private set         // rain;snow;sleet
    var temperatureHigh: Double? = null; private set    // °C
    var temperatureLow: Double? = null; private set     // °C
    var dewPoint: Double? = null; private set           // °C
    var humidity: Double? = null; private set
    var pressure: Double? = null; private set           // hPa
    var windSpeed: Double? = null; private set          // m/s
    var cloudCover: Double? = null; private set
    var uvIndex: Int? = null; private set
    var visibility: Double? = null; private set         // km
    var ozone: Double? = null; private set

    var fetchDate = Date(); private set

    companion object {

        fun fromResponse(responseData: ForecastResponse.Daily.DataPoint,
                         latitude: Double, longitude: Double, realm: Realm): ForecastPeriod {
            val forecastPeriod = realm.createObject<ForecastPeriod>()

            forecastPeriod.latitude = latitude
            forecastPeriod.longitude = longitude

            forecastPeriod.time = responseData.time
            forecastPeriod.summary = responseData.summary
            forecastPeriod.icon = responseData.icon
            forecastPeriod.precipIntensity = responseData.precipIntensity
            forecastPeriod.precipProbability = responseData.precipProbability
            forecastPeriod.precipType = responseData.precipType
            forecastPeriod.temperatureHigh = responseData.temperatureHigh
            forecastPeriod.temperatureLow = responseData.temperatureLow
            forecastPeriod.dewPoint = responseData.dewPoint
            forecastPeriod.humidity = responseData.humidity
            forecastPeriod.pressure = responseData.pressure
            forecastPeriod.windSpeed = responseData.windSpeed
            forecastPeriod.cloudCover = responseData.cloudCover
            forecastPeriod.uvIndex = responseData.uvIndex
            forecastPeriod.visibility = responseData.visibility
            forecastPeriod.ozone = responseData.ozone

            return forecastPeriod
        }

    }

}