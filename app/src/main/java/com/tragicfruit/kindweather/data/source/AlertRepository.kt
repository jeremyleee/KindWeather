package com.tragicfruit.kindweather.data.source

import com.tragicfruit.kindweather.data.*
import kotlinx.coroutines.flow.Flow

interface AlertRepository {

    suspend fun createAlert(priority: Int, type: WeatherAlertType): WeatherAlert

    suspend fun createParam(
        alert: WeatherAlert,
        type: ForecastDataType,
        rawDefaultLowerBound: Double?,
        rawDefaultUpperBound: Double?,
    ): WeatherAlertParam

    suspend fun findParamsForAlert(alertId: String): WeatherAlertWithParams

    fun getAllAlerts(): Flow<List<WeatherAlert>>

    suspend fun setAlertEnabled(alert: WeatherAlert, enabled: Boolean)

    suspend fun getAlertCount(): Int

    suspend fun findAlertMatchingForecast(forecast: ForecastPeriod): WeatherAlert?

    suspend fun setParamLowerBound(param: WeatherAlertParam, lowerBound: Double?)

    suspend fun setParamUpperBound(param: WeatherAlertParam, upperBound: Double?)

    suspend fun resetParamsToDefault(params: List<WeatherAlertParam>)
}