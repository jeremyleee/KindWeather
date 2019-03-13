package com.tragicfruit.weatherapp.controllers

import com.google.gson.annotations.SerializedName
import com.tragicfruit.weatherapp.BuildConfig
import com.tragicfruit.weatherapp.model.ForecastPeriod
import com.tragicfruit.weatherapp.utils.WCallback
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

private interface OpenWeatherAPIService {

    @GET("forecast")
    fun fetchForecast(@Query("lat") latitude: Double, @Query("lon") longitude: Double): Call<ForecastResponse>

    @GET("forecast")
    fun fetchForecast(@Query("q") cityName: String): Call<ForecastResponse>

}

data class ForecastResponse(val city: City, val cnt: Int, val list: List<Item>) {

    data class City(val id: Int, val name: String, val coord: Coord, val country: String) {
        data class Coord(val lat: Double, val lon: Double)
    }

    data class Item(val dt: Long,
                    val main: Main?,
                    val weather: List<Weather>?,
                    val clouds: Clouds?,
                    val wind: Wind?,
                    val rain: Rain?,
                    val snow: Snow?) {

        data class Main(val temp: Double, val temp_min: Double, val temp_max: Double, val pressure: Double,
                        val sea_level: Double, val grnd_level: Double, val humidity: Int)
        data class Weather(val id: Int, val main: String, val description: String, val icon: String)
        data class Clouds(val all: Int)
        data class Wind(val speed: Double, val deg: Double)
        data class Rain(@SerializedName("3h") val threeHour: Double)
        data class Snow(@SerializedName("3h") val threeHour: Double)
    }
}
