package com.tragicfruit.kindweather.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.tragicfruit.kindweather.data.model.ForecastDataPoint
import com.tragicfruit.kindweather.data.model.ForecastDataType

@Dao
interface ForecastDataPointDao {

    @Insert
    suspend fun insertAll(vararg dataPoints: ForecastDataPoint)

    @Query("SELECT * FROM datapoints WHERE forecastId = :forecastId AND dataType = :type")
    suspend fun loadDataPointForType(forecastId: String, type: ForecastDataType): ForecastDataPoint?

    @Query("DELETE FROM datapoints WHERE fetchedTime < :time")
    suspend fun deleteFetchedBefore(time: Long)
}
