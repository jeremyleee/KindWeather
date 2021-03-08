package com.tragicfruit.kindweather.data.source.local

import androidx.room.*
import com.tragicfruit.kindweather.data.WeatherAlert
import com.tragicfruit.kindweather.data.WeatherAlertWithParams
import kotlinx.coroutines.flow.Flow

@Dao
interface AlertDao {

    @Insert
    suspend fun insert(alert: WeatherAlert)

    @Update
    suspend fun update(alert: WeatherAlert)

    @Query("SELECT * FROM alerts ORDER BY enabled DESC, priority ASC")
    fun loadAll(): Flow<List<WeatherAlert>>

    @Query("SELECT COUNT(id) FROM alerts")
    suspend fun loadCount(): Int

    @Transaction
    @Query("SELECT * FROM alerts WHERE id = :alertId")
    suspend fun loadAlertWithParams(alertId: String): WeatherAlertWithParams

    @Transaction
    @Query("""
        SELECT * FROM alerts 
        INNER JOIN params ON params.alertId = alerts.id
        INNER JOIN datapoints ON datapoints.dataType = params.dataType
        WHERE datapoints.forecastId = :forecastId AND alerts.enabled
        GROUP BY alertId HAVING MIN(
            (params.rawLowerBound <= datapoints.rawValue OR params.rawLowerBound IS NULL) AND
            (params.rawUpperBound >= datapoints.rawValue OR params.rawUpperBound IS NULL)
        ) = 1
        ORDER BY priority ASC
    """)
    suspend fun loadAlertMatchingForecast(forecastId: String): WeatherAlert?
}
