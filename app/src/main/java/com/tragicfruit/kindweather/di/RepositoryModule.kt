package com.tragicfruit.kindweather.di

import com.tragicfruit.kindweather.data.source.AlertRepository
import com.tragicfruit.kindweather.data.source.DefaultAlertRepository
import com.tragicfruit.kindweather.data.source.DefaultNotificationRepository
import com.tragicfruit.kindweather.data.source.NotificationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindAlertRepository(
        repository: DefaultAlertRepository
    ): AlertRepository

    @Binds
    abstract fun bindNotificationRepository(
        repository: DefaultNotificationRepository
    ): NotificationRepository
}
