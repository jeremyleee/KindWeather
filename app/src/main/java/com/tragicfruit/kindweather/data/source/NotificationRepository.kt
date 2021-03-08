package com.tragicfruit.kindweather.data.source

import androidx.annotation.ColorInt
import com.tragicfruit.kindweather.data.ForecastIcon
import com.tragicfruit.kindweather.data.WeatherNotification
import kotlinx.coroutines.flow.Flow

interface NotificationRepository {
    
    suspend fun createNotification(
        description: String,
        @ColorInt color: Int,
        forecastIcon: ForecastIcon,
        rawTempHigh: Double?,
        rawTempLow: Double?,
        rawPrecipProbability: Double?,
        latitude: Double,
        longitude: Double,
    ): WeatherNotification

    suspend fun findNotification(id: String): WeatherNotification

    fun getAllNotifications(): Flow<List<WeatherNotification>>
}