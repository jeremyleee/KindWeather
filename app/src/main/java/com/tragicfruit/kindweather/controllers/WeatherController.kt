package com.tragicfruit.kindweather.controllers

import com.tragicfruit.kindweather.BuildConfig
import com.tragicfruit.kindweather.model.ForecastData
import com.tragicfruit.kindweather.model.ForecastPeriod
import com.tragicfruit.kindweather.utils.WCallback
import io.realm.Realm
import io.realm.kotlin.where
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import timber.log.Timber

object WeatherController {

    private lateinit var service: DarkSkyAPIService

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

}

private interface DarkSkyAPIService {

    @GET("forecast/${BuildConfig.API_KEY}/{latitude},{longitude}?exclude=currently,minutely,hourly&units=si")
    fun fetchForecast(@Path("latitude") latitude: Double, @Path("longitude") longitude: Double): Call<ForecastResponse>

}
