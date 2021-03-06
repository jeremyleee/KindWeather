package com.tragicfruit.kindweather.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.tragicfruit.kindweather.data.ForecastDataPoint
import com.tragicfruit.kindweather.data.ForecastDataType

@Dao
interface ForecastDataPointDao {

    @Insert
    suspend fun insertAll(vararg dataPoints: ForecastDataPoint)

    @Query("SELECT * FROM datapoints WHERE forecastId = :forecastId AND dataType = :type")
    suspend fun loadDataPointForType(forecastId: String, type: ForecastDataType): ForecastDataPoint?

    @Query("DELETE FROM datapoints WHERE fetchedTime < :time")
    suspend fun deleteFetchedBefore(time: Long)
}
