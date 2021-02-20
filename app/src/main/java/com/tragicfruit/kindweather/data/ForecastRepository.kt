package com.tragicfruit.kindweather.data

import com.tragicfruit.kindweather.api.DarkSkyAPIService
import com.tragicfruit.kindweather.model.ForecastData
import com.tragicfruit.kindweather.model.ForecastPeriod
import io.realm.Realm
import io.realm.kotlin.where
import timber.log.Timber
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ForecastRepository @Inject constructor(
    private val apiService: DarkSkyAPIService
) {

    suspend fun fetchForecast(latitude: Double, longitude: Double): Boolean {
        val timestamp = System.currentTimeMillis()

        val realm = Realm.getDefaultInstance()
        try {
            val forecast = apiService.fetchForecast(latitude, longitude)
            realm.executeTransaction {
                it.copyToRealm(forecast.data)

                Timber.d("${forecast.data.count()} forecast data fetched")

                // Clean up old forecasts
                if (forecast.data.isNotEmpty()) {
                    deleteForecastsBefore(timestamp, it)
                }
            }
            return true

        } catch (e: Exception) {
            Timber.e(e, "Failed to fetch forecast")
            return false

        } finally {
            realm.close()
        }
    }

    fun findTodaysForecast(): ForecastPeriod? {
        val calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
        }
        val now = calendar.time
        calendar.add(Calendar.DAY_OF_MONTH, -1)
        val yesterday = calendar.time

        return Realm.getDefaultInstance().where<ForecastPeriod>()
            .greaterThan("time", TimeUnit.MILLISECONDS.toSeconds(yesterday.time))
            .lessThanOrEqualTo("time", TimeUnit.MILLISECONDS.toSeconds(now.time))
            .findFirst()
    }

    private fun deleteForecastsBefore(timestamp: Long, realm: Realm) {
        val oldForecasts = realm.where<ForecastPeriod>()
            .lessThan("fetchedTime", timestamp)
            .findAll()

        Timber.d("${oldForecasts.count()} old forecasts deleted")

        val oldForecastData = realm.where<ForecastData>()
            .lessThan("fetchedTime", timestamp)
            .findAll()

        Timber.d("${oldForecastData.count()} old forecast data deleted")

        oldForecasts.deleteAllFromRealm()
        oldForecastData.deleteAllFromRealm()
    }
}