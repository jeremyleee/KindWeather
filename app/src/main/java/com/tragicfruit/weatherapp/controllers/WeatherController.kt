package com.tragicfruit.weatherapp.controllers

import com.tragicfruit.weatherapp.BuildConfig
import com.tragicfruit.weatherapp.model.ForecastPeriod
import io.realm.Realm
import io.realm.kotlin.where
import okhttp3.OkHttpClient
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import timber.log.Timber
import java.util.concurrent.TimeUnit

object WeatherController {

    private lateinit var service: OpenWeatherAPIService

    fun init() {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor { chain ->
                var request = chain.request()
                val url = request.url().newBuilder().addQueryParameter("APPID", BuildConfig.API_KEY).build()
                request = request.newBuilder().url(url).build()
                chain.proceed(request)
            }
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        service = retrofit.create()
    }

    fun fetchForecast(latitude: Double, longitude: Double, callback: WCallback) {
        service.fetchForecast(latitude, longitude).enqueue(object : Callback<ForecastResponse> {

                override fun onResponse(call: Call<ForecastResponse>, response: Response<ForecastResponse>) {
                    if (response.isSuccessful) {
                        Realm.getDefaultInstance().executeTransaction { realm ->
                            response.body()?.let { forecastResponse ->
                                for (forecastItem in forecastResponse.list) {
                                    ForecastPeriod.fromResponse(forecastItem, forecastResponse.city, realm)
                                }
                                Timber.d("${forecastResponse.list.count()} forecast data fetched")
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

    fun fetchForecast(cityName: String, callback: WCallback) {
        service.fetchForecast(cityName).enqueue(object : Callback<ForecastResponse> {

            override fun onResponse(call: Call<ForecastResponse>, response: Response<ForecastResponse>) {
                if (response.isSuccessful) {
                    Realm.getDefaultInstance().executeTransaction { realm ->
                        response.body()?.let { forecastResponse ->
                            for (forecastItem in forecastResponse.list) {
                                ForecastPeriod.fromResponse(forecastItem, forecastResponse.city, realm)
                            }
                            Timber.d("${forecastResponse.list.count()} forecasts fetched")
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

    fun deleteOldForecasts() {
        Realm.getDefaultInstance().executeTransaction {
            val oldForecasts = it.where<ForecastPeriod>()
                .lessThan("timeOfData", TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()))
                .findAll()

            Timber.d("${oldForecasts.count()} old forecasts deleted")

            oldForecasts.deleteAllFromRealm()
        }
    }

}

interface OpenWeatherAPIService {

    @GET("forecast")
    fun fetchForecast(@Query("lat") latitude: Double, @Query("lon") longitude: Double): Call<ForecastResponse>

    @GET("forecast")
    fun fetchForecast(@Query("q") cityName: String): Call<ForecastResponse>

}
