package com.tragicfruit.kindweather.data

import androidx.annotation.ColorInt
import com.tragicfruit.kindweather.model.ForecastPeriod
import com.tragicfruit.kindweather.model.WeatherNotification
import io.realm.Realm
import io.realm.RealmResults
import io.realm.Sort
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import java.util.*
import javax.inject.Inject

class NotificationRepository @Inject constructor() {
    
    fun createNotification(description: String, forecast: ForecastPeriod, @ColorInt color: Int) {
        Realm.getDefaultInstance().executeTransaction { realm ->
            realm.createObject<WeatherNotification>().also {
                it.id = UUID.randomUUID().toString()
                it.createdAt = Date()
                it.description = description
                it.forecast = forecast
                it.color = color
            }
        }
    }

    fun getAllNotifications(): RealmResults<WeatherNotification> {
        return Realm.getDefaultInstance()
            .where<WeatherNotification>()
            .sort("createdAt", Sort.DESCENDING)
            .findAll()
    }
}