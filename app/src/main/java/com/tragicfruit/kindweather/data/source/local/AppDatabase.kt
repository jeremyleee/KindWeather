package com.tragicfruit.kindweather.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tragicfruit.kindweather.data.ForecastDataPoint
import com.tragicfruit.kindweather.data.ForecastPeriod
import com.tragicfruit.kindweather.data.WeatherAlert
import com.tragicfruit.kindweather.data.WeatherAlertParam
import com.tragicfruit.kindweather.data.WeatherNotification

@Database(
    entities = [
        WeatherNotification::class,
        WeatherAlert::class,
        WeatherAlertParam::class,
        ForecastPeriod::class,
        ForecastDataPoint::class
    ],
    version = 2
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun notificationDao(): NotificationDao
    abstract fun alertDao(): AlertDao
    abstract fun alertParamDao(): AlertParamDao
    abstract fun forecastPeriodDao(): ForecastPeriodDao
    abstract fun forecastDataPointDao(): ForecastDataPointDao
}
