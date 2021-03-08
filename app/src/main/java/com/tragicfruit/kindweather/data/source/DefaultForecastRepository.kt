package com.tragicfruit.kindweather.data.source

import com.tragicfruit.kindweather.data.ForecastDataPoint
import com.tragicfruit.kindweather.data.ForecastDataType
import com.tragicfruit.kindweather.data.ForecastPeriod
import com.tragicfruit.kindweather.data.source.local.ForecastDataPointDao
import com.tragicfruit.kindweather.data.source.local.ForecastPeriodDao
import com.tragicfruit.kindweather.data.source.remote.DarkSkyAPIService
import com.tragicfruit.kindweather.di.IoDispatcher
import java.util.Calendar
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import timber.log.Timber

class DefaultForecastRepository @Inject constructor(
    private val periodDao: ForecastPeriodDao,
    private val dataPointDao: ForecastDataPointDao,
    private val apiService: DarkSkyAPIService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ForecastRepository {

    override suspend fun fetchForecast(latitude: Double, longitude: Double): Boolean {
        val timestamp = System.currentTimeMillis()

        return withContext(ioDispatcher) {
            try {
                val forecast = apiService.fetchForecast(latitude, longitude)
                periodDao.insertAll(*forecast.periods.toTypedArray())
                dataPointDao.insertAll(*forecast.dataPoints.toTypedArray())

                Timber.d("${forecast.periods.count()} forecast data fetched")

                // Clean up old forecasts
                if (forecast.periods.isNotEmpty()) {
                    deleteForecastsBefore(timestamp)
                }
                true
            } catch (e: Exception) {
                Timber.e(e, "Failed to fetch forecast")
                false
            }
        }
    }

    override suspend fun findDataPointForType(
        forecast: ForecastPeriod,
        type: ForecastDataType
    ): ForecastDataPoint? {
        return withContext(ioDispatcher) {
            dataPointDao.loadDataPointForType(forecast.id, type)
        }
    }

    override suspend fun findForecastForToday(): ForecastPeriod? {
        val calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
        }
        val now = calendar.time
        calendar.add(Calendar.DAY_OF_MONTH, -1)
        val yesterday = calendar.time

        return withContext(ioDispatcher) {
            periodDao.loadReportedBetween(
                minTime = TimeUnit.MILLISECONDS.toSeconds(yesterday.time),
                maxTime = TimeUnit.MILLISECONDS.toSeconds(now.time)
            )
        }
    }

    private suspend fun deleteForecastsBefore(timestamp: Long) {
        withContext(ioDispatcher) {
            periodDao.deleteFetchedBefore(timestamp)
            dataPointDao.deleteFetchedBefore(timestamp)
            Timber.d("Old forecasts deleted")
        }
    }
}
