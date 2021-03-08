package com.tragicfruit.kindweather.data.source

import com.tragicfruit.kindweather.data.ForecastDataPoint
import com.tragicfruit.kindweather.data.ForecastDataType
import com.tragicfruit.kindweather.data.ForecastPeriod

interface ForecastRepository {

    suspend fun fetchForecast(latitude: Double, longitude: Double): Boolean

    suspend fun findDataPointForType(
        forecast: ForecastPeriod,
        type: ForecastDataType
    ): ForecastDataPoint?

    suspend fun findForecastForToday(): ForecastPeriod?
}
