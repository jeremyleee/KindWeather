package com.tragicfruit.kindweather.data

import com.tragicfruit.kindweather.api.DarkSkyAPIService
import com.tragicfruit.kindweather.api.ForecastResponse
import com.tragicfruit.kindweather.model.ForecastData
import com.tragicfruit.kindweather.model.ForecastPeriod
import com.tragicfruit.kindweather.model.ForecastType
import com.tragicfruit.kindweather.utils.KWCallback
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class ForecastRepository @Inject constructor(
    private val apiService: DarkSkyAPIService
) {

    fun fetchForecast(latitude: Double, longitude: Double, callback: KWCallback) {
        apiService.fetchForecast(latitude, longitude).enqueue(object : Callback<ForecastResponse> {

            override fun onResponse(call: Call<ForecastResponse>, response: Response<ForecastResponse>) {
                if (response.isSuccessful) {
                    Realm.getDefaultInstance().executeTransaction { realm ->
                        response.body()?.let { forecastResponse ->
                            val timestamp = System.currentTimeMillis()

                            for (dailyItem in forecastResponse.daily.data) {
                                fromResponse(dailyItem, forecastResponse.latitude, forecastResponse.longitude, realm)
                            }
                            Timber.d("${forecastResponse.daily.data.count()} forecast data fetched")

                            // Clean up old forecasts
                            if (forecastResponse.daily.data.isNotEmpty()) {
                                deleteForecastsBefore(timestamp, realm)
                            }
                        }
                    }
                }

                callback(response.isSuccessful, response.code(), response.message())
            }

            override fun onFailure(call: Call<ForecastResponse>, t: Throwable) {
                callback(false, null, null)
            }

        })
    }

    private fun deleteForecastsBefore(timestamp: Long, realm: Realm) {
        val oldForecasts = realm.where<ForecastPeriod>()
            .lessThan("fetchedTime", timestamp)
            .equalTo("displayOnly", false)
            .findAll()

        Timber.d("${oldForecasts.count()} old forecasts deleted")

        val oldForecastData = realm.where<ForecastData>()
            .lessThan("fetchedTime", timestamp)
            .equalTo("displayOnly", false)
            .findAll()

        Timber.d("${oldForecastData.count()} old forecast data deleted")

        oldForecasts.deleteAllFromRealm()
        oldForecastData.deleteAllFromRealm()
    }

    fun findForecastPeriod(id: String): ForecastPeriod? {
        return Realm.getDefaultInstance()
            .where<ForecastPeriod>()
            .equalTo("id", id)
            .findFirst()
    }

    fun createData(type: ForecastType, rawValue: Double?, realm: Realm): ForecastData? {
        rawValue ?: return null

        val data = realm.createObject<ForecastData>()
        data.type = type.name
        data.rawValue = rawValue
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