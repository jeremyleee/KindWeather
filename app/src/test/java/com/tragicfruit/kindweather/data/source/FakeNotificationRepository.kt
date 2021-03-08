package com.tragicfruit.kindweather.data.source

import com.tragicfruit.kindweather.data.ForecastIcon
import com.tragicfruit.kindweather.data.WeatherNotification
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.util.*

class FakeNotificationRepository : NotificationRepository {

    val notificationMap: LinkedHashMap<String, WeatherNotification> = LinkedHashMap()

    override suspend fun createNotification(
        description: String,
        color: Int,
        forecastIcon: ForecastIcon,
        rawTempHigh: Double?,
        rawTempLow: Double?,
        rawPrecipProbability: Double?,
        latitude: Double,
        longitude: Double
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
            notificationMap[it.id] = it
        }
    }

    override suspend fun findNotification(id: String): WeatherNotification {
        return notificationMap[id]!!
    }

    override fun getAllNotifications(): Flow<List<WeatherNotification>> {
        return flowOf(notificationMap.values.toList())
    }
}