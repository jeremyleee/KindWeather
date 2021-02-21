package com.tragicfruit.kindweather.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tragicfruit.kindweather.data.db.dao.*
import com.tragicfruit.kindweather.data.model.*

@Database(
    entities = [
        WeatherNotification::class,
        WeatherAlert::class,
        WeatherAlertParam::class,
        ForecastPeriod::class,
        ForecastDataPoint::class
    ],
    version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun notificationDao(): NotificationDao
    abstract fun alertDao(): AlertDao
    abstract fun alertParamDao(): AlertParamDao
    abstract fun forecastPeriodDao(): ForecastPeriodDao
    abstract fun forecastDataPointDao(): ForecastDataPointDao
}
