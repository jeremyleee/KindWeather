package com.tragicfruit.kindweather.data.db

import androidx.room.TypeConverter
import com.tragicfruit.kindweather.data.model.ForecastDataType
import com.tragicfruit.kindweather.data.model.ForecastIcon
import com.tragicfruit.kindweather.data.model.WeatherAlertType
import java.util.*

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun toWeatherAlertType(value: String) = enumValueOf<WeatherAlertType>(value)

    @TypeConverter
    fun fromWeatherAlertType(value: WeatherAlertType) = value.name

    @TypeConverter
    fun toForecastType(value: String) = enumValueOf<ForecastDataType>(value)

    @TypeConverter
    fun fromForecastType(value: ForecastDataType) = value.name

    @TypeConverter
    fun toForecastIcon(value: String) = enumValueOf<ForecastIcon>(value)

    @TypeConverter
    fun fromForecastIcon(value: ForecastIcon) = value.name
}
