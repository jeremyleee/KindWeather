package com.tragicfruit.kindweather.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Update
import com.tragicfruit.kindweather.data.WeatherAlertParam

@Dao
interface AlertParamDao {

    @Insert
    suspend fun insert(param: WeatherAlertParam)

    @Update
    suspend fun update(param: WeatherAlertParam)

    @Update
    suspend fun updateAll(vararg params: WeatherAlertParam)
}
