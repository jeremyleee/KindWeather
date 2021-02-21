package com.tragicfruit.kindweather.data

import androidx.annotation.ColorInt
import androidx.lifecycle.LiveData
import com.tragicfruit.kindweather.data.db.dao.NotificationDao
import com.tragicfruit.kindweather.data.model.ForecastIcon
import com.tragicfruit.kindweather.data.model.WeatherNotification
import java.util.*
import javax.inject.Inject

class NotificationRepository @Inject constructor(
    private val dao: NotificationDao
) {
    
    suspend fun createNotification(
        description: String,
        @ColorInt color: Int,
        forecastIcon: ForecastIcon,
        rawTempHigh: Double?,
        rawTempLow: Double?,
        rawPrecipProbability: Double?,
        latitude: Double,
        longitude: Double,
    ): WeatherNotification {
        return WeatherNotification(
            id = UUID.randomUUID().toString(),
            createdAt = Date(),
            description = description,
            color = color,
            forecastIcon = forecastIcon,
            rawTempHigh = rawTempHigh,
            rawTempLow = rawTempLow,
            rawPrecipProbability = rawPrecipProbability,
            latitude = latitude,
            longitude = longitude
        ).also {
            dao.insert(it)
        }
    }

    fun findNotification(id: String): LiveData<WeatherNotification> {
        return dao.loadById(id)
    }

    fun getAllNotifications(): LiveData<List<WeatherNotification>> {
        return dao.loadAll()
    }
}