package com.tragicfruit.kindweather.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.tragicfruit.kindweather.data.ForecastPeriod

@Dao
interface ForecastPeriodDao {

    @Insert
    suspend fun insertAll(vararg forecasts: ForecastPeriod)

    @Query("SELECT * FROM forecast WHERE reportedTime BETWEEN :minTime AND :maxTime")
    suspend fun loadReportedBetween(minTime: Long, maxTime: Long): ForecastPeriod?

    @Query("DELETE FROM forecast WHERE fetchedTime < :time")
    suspend fun deleteFetchedBefore(time: Long)
}
