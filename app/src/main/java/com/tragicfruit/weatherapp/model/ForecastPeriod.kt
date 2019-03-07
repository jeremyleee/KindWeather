package com.tragicfruit.weatherapp.model

import io.realm.RealmObject
import java.util.*

/**
 * Forecast for a 3 hour period of time
 */
open class ForecastPeriod : RealmObject() {

    private var longitude = 0.0 // City longitude
    private var latitude = 0.0 // City latitude
    private var cityId = 0

    private var calculationTime: Date? = null

    private var weatherId = 0 // Weather condition id

    private var tempMax: ForecastInfo? = null
    private var tempMin: ForecastInfo? = null
    private var pressure: ForecastInfo? = null
    private var humidity: ForecastInfo? = null
    private var windSpeed: ForecastInfo? = null
    private var clouds: ForecastInfo? = null
    private var rain: ForecastInfo? = null
    private var snow: ForecastInfo? = null

}