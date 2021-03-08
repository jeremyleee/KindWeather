package com.tragicfruit.kindweather.di

import android.content.Context
import android.location.Geocoder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import java.util.Locale

@Module
@InstallIn(ViewModelComponent::class)
object LocationModule {

    @Provides
    @ViewModelScoped
    fun providesGeocoder(@ApplicationContext context: Context): Geocoder {
        return Geocoder(context, Locale.getDefault())
    }
}
