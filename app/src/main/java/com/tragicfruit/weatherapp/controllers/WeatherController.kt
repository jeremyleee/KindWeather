package com.tragicfruit.weatherapp.controllers

import com.tragicfruit.weatherapp.BuildConfig
import com.tragicfruit.weatherapp.model.ForecastData
import com.tragicfruit.weatherapp.model.ForecastPeriod
import com.tragicfruit.weatherapp.utils.WCallback
import io.realm.Realm
import io.realm.kotlin.where
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import timber.log.Timber

object WeatherController {

    private lateinit var service: OpenWeatherAPIService

    fun init() {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create()
    }

    fun fetchForecast(latitude: Double, longitude: Double, callback: WCallback) {
        service.fetchForecast(latitude, longitude).enqueue(object : Callback<ForecastResponse> {

                override fun onResponse(call: Call<ForecastResponse>, response: Response<ForecastResponse>) {
                    if (response.isSuccessful) {
                        Realm.getDefaultInstance().executeTransaction { realm ->
                            response.body()?.let { forecastResponse ->
                                val timestamp = System.currentTimeMillis()

                                for (dailyItem in forecastResponse.daily.data) {
                                    ForecastPeriod.fromResponse(dailyItem, forecastResponse.latitude, forecastResponse.longitude, realm)
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
            .findAll()

        Timber.d("${oldForecasts.count()} old forecasts deleted")

        val oldForecastData = realm.where<ForecastData>()
            .lessThan("fetchedTime", timestamp)
            .findAll()

        Timber.d("${oldForecastData.count()} old forecast data deleted")

        oldForecasts.deleteAllFromRealm()
        oldForecastData.deleteAllFromRealm()
    }

}

private interface OpenWeatherAPIService {

    @GET("forecast/${BuildConfig.API_KEY}/{latitude},{longitude}?exclude=currently,minutely,hourly&units=si")
    fun fetchForecast(@Path("latitude") latitude: Double, @Path("longitude") longitude: Double): Call<ForecastResponse>

}

data class ForecastResponse(val latitude: Double, val longitude: Double, val timezone: String, val daily: Daily) {

    data class Daily(val summary: String?, val icon: String?, val data: List<DataPoint>) {

        data class DataPoint(val time: Long,
                        val summary: String?,
                        val icon: String?,
                        val precipIntensity: Double?,
                        val precipProbability: Double?,
                        val precipType: String?,
                        val temperatureHigh: Double?,
                        val temperatureLow: Double?,
                        val dewPoint: Double?,
                        val humidity: Double?,
                        val pressure: Double?,
                        val windSpeed: Double?,
                        val windGust: Double?,
                        val cloudCover: Double?,
                        val uvIndex: Int?,
                        val visibility: Double?,
                        val ozone: Double?)

    }

}
