package com.tragicfruit.kindweather.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tragicfruit.kindweather.data.db.dao.AlertDao
import com.tragicfruit.kindweather.data.db.dao.AlertParamDao
import com.tragicfruit.kindweather.data.db.dao.NotificationDao
import com.tragicfruit.kindweather.data.model.WeatherAlert
import com.tragicfruit.kindweather.data.model.WeatherAlertParam
import com.tragicfruit.kindweather.data.model.WeatherNotification

@Database(
    entities = [WeatherNotification::class, WeatherAlert::class, WeatherAlertParam::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun notificationDao(): NotificationDao
    abstract fun alertDao(): AlertDao
    abstract fun alertParamDao(): AlertParamDao
}
