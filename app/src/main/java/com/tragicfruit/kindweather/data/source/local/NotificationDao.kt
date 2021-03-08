package com.tragicfruit.kindweather.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.tragicfruit.kindweather.data.WeatherNotification
import kotlinx.coroutines.flow.Flow

@Dao
interface NotificationDao {

    @Insert
    suspend fun insert(notification: WeatherNotification)

    @Query("SELECT * FROM notifications WHERE id = :id")
    suspend fun loadById(id: String): WeatherNotification

    @Query("SELECT * FROM notifications ORDER BY createdAt DESC")
    fun loadAll(): Flow<List<WeatherNotification>>
}
