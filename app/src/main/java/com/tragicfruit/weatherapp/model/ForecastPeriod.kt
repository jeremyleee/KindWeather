package com.tragicfruit.weatherapp.model

import com.tragicfruit.weatherapp.controllers.ForecastResponse
import io.realm.Realm
import io.realm.RealmObject
import io.realm.kotlin.createObject

/**
 * Forecast for a day
 */
open class ForecastPeriod : RealmObject() {

    var latitude = 0.0; private set
    var longitude = 0.0; private set

    var time: Long = 0; private set
    var summary: String? = null; private set
    var icon: String? = null; private set
    var rainIntensity: ForecastData? = null; private set    // mm/h
    var rainProbability: ForecastData? = null; private set  // mm/h
    var snowIntensity: ForecastData? = null; private set    // mm/h
    var snowProbability: ForecastData? = null; private set  // mm/h
    var temperatureHigh: ForecastData? = null; private set  // °C
    var temperatureLow: ForecastData? = null; private set   // °C
    var humidity: ForecastData? = null; private set
    var windSpeed: ForecastData? = null; private set        // m/s
    var uvIndex: ForecastData? = null; private set

    var fetchedTime: Long = 0; private set

    companion object {

        fun fromResponse(responseData: ForecastResponse.Daily.DataPoint,
                         latitude: Double, longitude: Double, realm: Realm): ForecastPeriod {
            val forecastPeriod = realm.createObject<ForecastPeriod>()

            forecastPeriod.latitude = latitude
            forecastPeriod.longitude = longitude

            forecastPeriod.time = responseData.time
            forecastPeriod.summary = responseData.summary
            forecastPeriod.icon = responseData.icon

            when (responseData.precipType) {
                "rain" -> {
                    forecastPeriod.rainIntensity = ForecastData.create(ForecastType.Rain_intensity, responseData.precipIntensity, realm)
                    forecastPeriod.rainProbability = ForecastData.create(ForecastType.Rain_probability, responseData.precipProbability, realm)
                }
                "snow" -> {
                    forecastPeriod.snowIntensity = ForecastData.create(ForecastType.Snow_intensity, responseData.precipIntensity, realm)
                    forecastPeriod.snowProbability = ForecastData.create(ForecastType.Snow_probability, responseData.precipProbability, realm)
                }
                "sleet" -> {
                    // Do nothing for now
                }
            }

            forecastPeriod.temperatureHigh = ForecastData.create(ForecastType.Temp_high, responseData.temperatureHigh, realm)
            forecastPeriod.temperatureLow = ForecastData.create(ForecastType.Temp_low, responseData.temperatureLow, realm)
            forecastPeriod.humidity = ForecastData.create(ForecastType.Humidity, responseData.humidity, realm)
            forecastPeriod.windSpeed = ForecastData.create(ForecastType.Wind_speed, responseData.windSpeed, realm)
            forecastPeriod.uvIndex = ForecastData.create(ForecastType.Uv_index, responseData.uvIndex?.toDouble(), realm)

            forecastPeriod.fetchedTime = System.currentTimeMillis()

            return forecastPeriod
        }

    }

}