package com.tragicfruit.kindweather.di

import android.content.Context
import com.tragicfruit.kindweather.BuildConfig
import com.tragicfruit.kindweather.api.DarkSkyAPIService
import com.tragicfruit.kindweather.utils.SharedPrefsHelper
import com.tragicfruit.kindweather.utils.ViewHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun providesApiService(retrofit: Retrofit): DarkSkyAPIService {
        return retrofit.create()
    }

    @Provides
    @Singleton
    fun providesRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .addConverterFactory(GsonConverterFactory.create())
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