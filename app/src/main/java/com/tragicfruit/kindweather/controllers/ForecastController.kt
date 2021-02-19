package com.tragicfruit.kindweather.controllers

import com.tragicfruit.kindweather.BuildConfig
import com.tragicfruit.kindweather.data.ForecastRepository
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
import javax.inject.Inject
import javax.inject.Singleton

class ForecastController @Inject constructor(
    private val forecastRepository: ForecastRepository
) {



}

interface DarkSkyAPIService {

    @GET("forecast/${BuildConfig.API_KEY}/{latitude},{longitude}?exclude=currently,minutely,hourly&units=si")
    fun fetchForecast(@Path("latitude") latitude: Double, @Path("longitude") longitude: Double): Call<ForecastResponse>
}
