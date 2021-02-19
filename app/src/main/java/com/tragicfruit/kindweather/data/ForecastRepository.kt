package com.tragicfruit.kindweather.data

import com.tragicfruit.kindweather.controllers.ForecastResponse
import com.tragicfruit.kindweather.model.ForecastData
import com.tragicfruit.kindweather.model.ForecastPeriod
import com.tragicfruit.kindweather.model.ForecastType
import io.realm.Realm
import io.realm.kotlin.createObject
import java.util.*
import javax.inject.Inject

class ForecastRepository @Inject constructor() {

    fun createData(type: ForecastType, rawValue: Double?, realm: Realm): ForecastData? {
        val value = rawValue ?: return null

        val data = realm.createObject<ForecastData>()
        data.type = type.name
        data.value = value
        data.fetchedTime = System.currentTimeMillis()
        return data
    }

    fun setDisplayed(data: ForecastData, displayed: Boolean) {
        Realm.getDefaultInstance().executeTransaction {
            data.displayOnly = displayed
        }
    }

    fun fromResponse(responseData: ForecastResponse.Daily.DataPoint,
                     latitude: Double, longitude: Double, realm: Realm): ForecastPeriod {
        val forecastPeriod = realm.createObject<ForecastPeriod>()

        forecastPeriod.id = UUID.randomUUID().toString()

        forecastPeriod.latitude = latitude
        forecastPeriod.longitude = longitude

        forecastPeriod.time = responseData.time
        forecastPeriod.summary = responseData.summary ?: forecastPeriod.summary
        forecastPeriod.icon = responseData.icon ?: forecastPeriod.icon

        forecastPeriod.data.add(createData(ForecastType.TEMP_HIGH, responseData.temperatureHigh, realm))
        forecastPeriod.data.add(createData(ForecastType.TEMP_LOW, responseData.temperatureLow, realm))
        forecastPeriod.data.add(createData(ForecastType.PRECIP_INTENSITY, responseData.precipIntensity, realm))
        forecastPeriod.data.add(createData(ForecastType.PRECIP_PROBABILITY, responseData.precipProbability, realm))
        forecastPeriod.data.add(createData(ForecastType.HUMIDITY, responseData.humidity, realm))
        forecastPeriod.data.add(createData(ForecastType.WIND_GUST, responseData.windGust, realm))
        forecastPeriod.data.add(createData(ForecastType.UV_INDEX, responseData.uvIndex?.toDouble(), realm))

        forecastPeriod.fetchedTime = System.currentTimeMillis()

        return forecastPeriod
    }

    fun setDisplayed(forecast: ForecastPeriod, displayed: Boolean) {
        Realm.getDefaultInstance().executeTransaction {
            forecast.displayOnly = displayed
        }
        forecast.data.forEach { data ->
            setDisplayed(data, displayed)
        }
    }
}