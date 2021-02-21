package com.tragicfruit.kindweather.data

import com.tragicfruit.kindweather.data.api.DarkSkyAPIService
import com.tragicfruit.kindweather.data.db.dao.ForecastDataPointDao
import com.tragicfruit.kindweather.data.db.dao.ForecastPeriodDao
import com.tragicfruit.kindweather.data.model.ForecastDataPoint
import com.tragicfruit.kindweather.data.model.ForecastDataType
import com.tragicfruit.kindweather.data.model.ForecastPeriod
import timber.log.Timber
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ForecastRepository @Inject constructor(
    private val periodDao: ForecastPeriodDao,
    private val dataPointDao: ForecastDataPointDao,
    private val apiService: DarkSkyAPIService
) {

    suspend fun fetchForecast(latitude: Double, longitude: Double): Boolean {
        val timestamp = System.currentTimeMillis()

        try {
            val forecast = apiService.fetchForecast(latitude, longitude)
            periodDao.insertAll(*forecast.periods.toTypedArray())
            dataPointDao.insertAll(*forecast.dataPoints.toTypedArray())

            Timber.d("${forecast.periods.count()} forecast data fetched")

            // Clean up old forecasts
            if (forecast.periods.isNotEmpty()) {
                deleteForecastsBefore(timestamp)
            }
            return true

        } catch (e: Exception) {
            Timber.e(e, "Failed to fetch forecast")
            return false

        }
    }

    suspend fun findDataPointForType(forecast: ForecastPeriod, type: ForecastDataType): ForecastDataPoint? {
        return dataPointDao.loadDataPointForType(forecast.id, type)
    }

    suspend fun findForecastForToday(): ForecastPeriod? {
        val calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
        }
        val now = calendar.time
        calendar.add(Calendar.DAY_OF_MONTH, -1)
        val yesterday = calendar.time

        return periodDao.loadReportedBetween(
            minTime = TimeUnit.MILLISECONDS.toSeconds(yesterday.time),
            maxTime = TimeUnit.MILLISECONDS.toSeconds(now.time)
        )
    }

    private suspend fun deleteForecastsBefore(timestamp: Long) {
        periodDao.deleteFetchedBefore(timestamp)
        dataPointDao.deleteFetchedBefore(timestamp)
        Timber.d("Old forecasts deleted")
    }
}