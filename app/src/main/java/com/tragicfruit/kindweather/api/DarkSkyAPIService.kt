package com.tragicfruit.kindweather.api

import com.tragicfruit.kindweather.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Path

interface DarkSkyAPIService {

    @GET("forecast/${BuildConfig.API_KEY}/{latitude},{longitude}?exclude=currently,minutely,hourly&units=si")
    suspend fun fetchForecast(
        @Path("latitude") latitude: Double,
        @Path("longitude") longitude: Double
    ): Forecast
}
