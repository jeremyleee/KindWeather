package com.tragicfruit.kindweather.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tragicfruit.kindweather.data.model.WeatherNotification

@Database(entities = [WeatherNotification::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun notificationDao(): NotificationDao
}
