package com.tragicfruit.kindweather.di

import android.content.Context
import androidx.room.Room
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.tragicfruit.kindweather.BuildConfig
import com.tragicfruit.kindweather.data.api.DarkSkyAPIService
import com.tragicfruit.kindweather.data.db.AppDatabase
import com.tragicfruit.kindweather.data.db.dao.*
import com.tragicfruit.kindweather.utils.SharedPrefsHelper
import com.tragicfruit.kindweather.utils.ViewHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun providesAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "kindweather-db"
        ).build()
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

    @Provides
    @Singleton
    fun providesApiService(retrofit: Retrofit): DarkSkyAPIService {
        return retrofit.create()
    }

    @ExperimentalSerializationApi
    @Provides
    @Singleton
    fun providesRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .addConverterFactory(Json {
                isLenient = true
                ignoreUnknownKeys = true
            }.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    @Provides
    @Singleton
    fun providesSharedPrefsHelper(@ApplicationContext context: Context): SharedPrefsHelper {
        return SharedPrefsHelper(context)
    }

    @Provides
    @Singleton
    fun providesViewHelper(@ApplicationContext context: Context): ViewHelper {
        return ViewHelper(context)
    }
}