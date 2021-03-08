package com.tragicfruit.kindweather.data

import androidx.annotation.DrawableRes
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tragicfruit.kindweather.R

/**
 * Forecast for a day
 */
@Entity(tableName = "forecast")
data class ForecastPeriod(
    @PrimaryKey val id: String,
    val latitude: Double,
    val longitude: Double,
    val reportedTime: Long,
    val summary: String?,
    val icon: ForecastIcon,
    val fetchedTime: Long
)

enum class ForecastIcon(@DrawableRes val iconRes: Int = 0) {
    ClearDay(R.drawable.ic_sunny),
    ClearNight(R.drawable.ic_sunny),
    Rain(R.drawable.ic_rain),
    Snow(R.drawable.ic_snow),
    Sleet(R.drawable.ic_snow),
    Wind(R.drawable.ic_windy),
    Fog(R.drawable.ic_fog),
    Cloudy(R.drawable.ic_cloudy),
    PartlyCloudyDay(R.drawable.ic_partly_cloudy),
    PartlyCloudyNight(R.drawable.ic_partly_cloudy),

    Unknown
}
