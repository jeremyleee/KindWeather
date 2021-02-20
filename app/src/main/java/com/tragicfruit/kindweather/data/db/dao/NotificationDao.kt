package com.tragicfruit.kindweather.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.tragicfruit.kindweather.data.model.WeatherNotification

@Dao
interface NotificationDao {

    @Insert
    suspend fun insert(notification: WeatherNotification)

    @Query("SELECT * FROM notifications WHERE id = :id")
    fun loadById(id: String): LiveData<WeatherNotification>

    @Query("SELECT * FROM notifications ORDER BY createdAt DESC")
    fun loadAll(): LiveData<List<WeatherNotification>>
}
