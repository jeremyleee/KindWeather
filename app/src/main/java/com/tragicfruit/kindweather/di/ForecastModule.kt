package com.tragicfruit.kindweather.di

import com.tragicfruit.kindweather.controllers.ForecastController
import com.tragicfruit.kindweather.data.ForecastRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class ForecastModule {

    @Provides
    fun providesForecastController(repository: ForecastRepository): ForecastController {
        return ForecastController(repository)
    }
}