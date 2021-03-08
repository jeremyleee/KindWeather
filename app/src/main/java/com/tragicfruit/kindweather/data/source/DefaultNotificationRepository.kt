package com.tragicfruit.kindweather.data.source

import androidx.annotation.ColorInt
import com.tragicfruit.kindweather.data.ForecastIcon
import com.tragicfruit.kindweather.data.WeatherNotification
import com.tragicfruit.kindweather.data.source.local.NotificationDao
import com.tragicfruit.kindweather.di.IoDispatcher
import java.util.Date
import java.util.UUID
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class DefaultNotificationRepository @Inject constructor(
    private val dao: NotificationDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : NotificationRepository {

    override suspend fun createNotification(
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

    override suspend fun findNotification(id: String): WeatherNotification {
        return withContext(ioDispatcher) {
            dao.loadById(id)
        }
    }

    override fun getAllNotifications(): Flow<List<WeatherNotification>> {
        return dao.loadAll()
    }
}
