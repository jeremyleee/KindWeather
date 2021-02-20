package com.tragicfruit.kindweather.data

import androidx.annotation.ColorInt
import androidx.lifecycle.LiveData
import com.tragicfruit.kindweather.data.db.dao.NotificationDao
import com.tragicfruit.kindweather.data.model.ForecastPeriod
import com.tragicfruit.kindweather.data.model.ForecastType
import com.tragicfruit.kindweather.data.model.WeatherNotification
import java.util.*
import javax.inject.Inject

class NotificationRepository @Inject constructor(
    private val dao: NotificationDao
) {
    
    suspend fun createNotification(
        description: String,
        forecast: ForecastPeriod,
        @ColorInt color: Int
    ): WeatherNotification {
        return WeatherNotification(
            id = UUID.randomUUID().toString(),
            createdAt = Date(),
            description = description,
            color = color,
            forecastIconName = forecast.icon,
            rawTempHigh = forecast.getDataForType(ForecastType.TempHigh)?.rawValue,
            rawTempLow = forecast.getDataForType(ForecastType.TempLow)?.rawValue,
            rawPrecipProbability = forecast.getDataForType(ForecastType.PrecipProbability)?.rawValue,
            latitude = forecast.latitude,
            longitude = forecast.longitude
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