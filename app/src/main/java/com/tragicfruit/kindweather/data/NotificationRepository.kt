package com.tragicfruit.kindweather.data

import androidx.annotation.ColorInt
import com.tragicfruit.kindweather.model.ForecastPeriod
import com.tragicfruit.kindweather.model.ForecastType
import com.tragicfruit.kindweather.model.WeatherNotification
import io.realm.Realm
import io.realm.RealmResults
import io.realm.Sort
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import java.util.*
import javax.inject.Inject

class NotificationRepository @Inject constructor() {
    
    fun createNotification(description: String, forecast: ForecastPeriod, @ColorInt color: Int): WeatherNotification {
        val realm = Realm.getDefaultInstance()
        realm.beginTransaction()
        val notification = realm.createObject<WeatherNotification>().also {
            it.id = UUID.randomUUID().toString()
            it.createdAt = Date()
            it.description = description
            it.color = color

            it.forecastIcon = forecast.icon
            it.rawTempHigh = forecast.getDataForType(ForecastType.TEMP_HIGH)?.rawValue ?: it.rawTempHigh
            it.rawTempLow = forecast.getDataForType(ForecastType.TEMP_LOW)?.rawValue ?: it.rawTempLow
            it.rawPrecipProbability = forecast.getDataForType(ForecastType.PRECIP_PROBABILITY)?.rawValue ?: it.rawPrecipProbability
            it.latitude = forecast.latitude
            it.longitude = forecast.longitude
        }
        realm.commitTransaction()
        realm.close()
        return notification
    }

    fun findNotification(id: String): WeatherNotification? {
        return Realm.getDefaultInstance()
            .where<WeatherNotification>()
            .equalTo("id", id)
            .findFirst()
    }

    fun getAllNotifications(): RealmResults<WeatherNotification> {
        return Realm.getDefaultInstance()
            .where<WeatherNotification>()
            .sort("createdAt", Sort.DESCENDING)
            .findAll()
    }
}