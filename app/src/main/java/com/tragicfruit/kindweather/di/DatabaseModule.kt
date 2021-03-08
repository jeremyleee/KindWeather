package com.tragicfruit.kindweather.di

import android.content.Context
import androidx.room.Room
import com.tragicfruit.kindweather.data.source.local.AlertDao
import com.tragicfruit.kindweather.data.source.local.AlertParamDao
import com.tragicfruit.kindweather.data.source.local.AppDatabase
import com.tragicfruit.kindweather.data.source.local.ForecastDataPointDao
import com.tragicfruit.kindweather.data.source.local.ForecastPeriodDao
import com.tragicfruit.kindweather.data.source.local.NotificationDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providesAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "kindweather-db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun providesNotificationDao(database: AppDatabase): NotificationDao {
        return database.notificationDao()
    }

    @Provides
    @Singleton
    fun providesAlertDao(database: AppDatabase): AlertDao {
        return database.alertDao()
    }

    @Provides
    @Singleton
    fun providesAlertParamDao(database: AppDatabase): AlertParamDao {
        return database.alertParamDao()
    }

    @Provides
    @Singleton
    fun providesForecastPeriodDao(database: AppDatabase): ForecastPeriodDao {
        return database.forecastPeriodDao()
    }

    @Provides
    @Singleton
    fun providesForecastDataPointDao(database: AppDatabase): ForecastDataPointDao {
        return database.forecastDataPointDao()
    }
}
