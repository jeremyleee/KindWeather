package com.tragicfruit.kindweather.data

import androidx.annotation.ColorInt
import com.tragicfruit.kindweather.data.db.dao.NotificationDao
import com.tragicfruit.kindweather.data.model.ForecastIcon
import com.tragicfruit.kindweather.data.model.WeatherNotification
import com.tragicfruit.kindweather.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

class NotificationRepository @Inject constructor(
    private val dao: NotificationDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
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
        return withContext(ioDispatcher) {
            WeatherNotification(
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
    }

    suspend fun findNotification(id: String): WeatherNotification {
        return withContext(ioDispatcher) {
            dao.loadById(id)
        }
    }

    fun getAllNotifications(): Flow<List<WeatherNotification>> {
        return dao.loadAll()
    }
}