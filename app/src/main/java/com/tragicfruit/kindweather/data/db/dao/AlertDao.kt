package com.tragicfruit.kindweather.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.tragicfruit.kindweather.data.model.WeatherAlert
import com.tragicfruit.kindweather.data.model.WeatherAlertWithParams

@Dao
interface AlertDao {

    @Insert
    suspend fun insert(alert: WeatherAlert)

    @Update
    suspend fun update(alert: WeatherAlert)

    @Query("SELECT * FROM alerts WHERE id = :id")
    fun loadById(id: String): LiveData<WeatherAlert>

    @Query("SELECT * FROM alerts ORDER BY enabled DESC, priority ASC")
    fun loadAll(): LiveData<List<WeatherAlert>>

    @Query("SELECT COUNT(id) FROM alerts")
    suspend fun loadCount(): Int

    @Transaction
    @Query("SELECT * FROM alerts WHERE id = :alertId")
    fun loadAlertWithParams(alertId: String): LiveData<WeatherAlertWithParams>

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
