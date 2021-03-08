package com.tragicfruit.kindweather.data.source

import com.tragicfruit.kindweather.data.ForecastDataType
import com.tragicfruit.kindweather.data.ForecastPeriod
import com.tragicfruit.kindweather.data.WeatherAlert
import com.tragicfruit.kindweather.data.WeatherAlertParam
import com.tragicfruit.kindweather.data.WeatherAlertType
import com.tragicfruit.kindweather.data.WeatherAlertWithParams
import java.util.LinkedHashMap
import java.util.UUID
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeAlertRepository : AlertRepository {

    val alertMap: LinkedHashMap<String, WeatherAlert> = LinkedHashMap()
    val paramMap: LinkedHashMap<String, MutableList<WeatherAlertParam>> = LinkedHashMap()

    var useImperialUnits = false

    override suspend fun createAlert(priority: Int, type: WeatherAlertType): WeatherAlert {
        return WeatherAlert(
            id = UUID.randomUUID().toString(),
            alertType = type,
            priority = priority
        ).also {
            alertMap[it.id] = it
        }
    }

    override suspend fun createParam(
        alert: WeatherAlert,
        type: ForecastDataType,
        rawDefaultLowerBound: Double?,
        rawDefaultUpperBound: Double?
    ): WeatherAlertParam {
        return WeatherAlertParam(
            id = UUID.randomUUID().toString(),
            alertId = alert.id,
            dataType = type,
            rawDefaultLowerBound = rawDefaultLowerBound,
            rawDefaultUpperBound = rawDefaultUpperBound,
            rawLowerBound = rawDefaultLowerBound,
            rawUpperBound = rawDefaultUpperBound
        ).also {
            paramMap.getOrPut(alert.id, { mutableListOf() }).add(it)
        }
    }

    override suspend fun findParamsForAlert(alertId: String): WeatherAlertWithParams {
        return WeatherAlertWithParams(
            alert = alertMap[alertId]!!,
            params = paramMap[alertId] ?: emptyList()
        )
    }

    override fun getAllAlerts(): Flow<List<WeatherAlert>> {
        return flowOf(alertMap.values.toList())
    }

    override suspend fun setAlertEnabled(alert: WeatherAlert, enabled: Boolean) {
        alertMap[alert.id] = alert.apply {
            this.enabled = enabled
        }
    }

    override suspend fun getAlertCount(): Int {
        return alertMap.values.size
    }

    override suspend fun findAlertMatchingForecast(forecast: ForecastPeriod): WeatherAlert? {
        // TODO: implement logic for finding alert
        return null
    }

    override suspend fun setParamLowerBound(param: WeatherAlertParam, lowerBound: Double?) {
        param.setLowerBound(lowerBound, useImperialUnits)
        paramMap[param.alertId]?.apply {
            if (removeIf { it.id == param.id }) {
                add(param)
            }
        }
    }

    override suspend fun setParamUpperBound(param: WeatherAlertParam, upperBound: Double?) {
        param.setUpperBound(upperBound, useImperialUnits)
        paramMap[param.alertId]?.apply {
            if (removeIf { it.id == param.id }) {
                add(param)
            }
        }
    }

    override suspend fun resetParamsToDefault(params: List<WeatherAlertParam>) {
        params.forEach { param ->
            param.rawLowerBound = param.rawDefaultLowerBound
            param.rawUpperBound = param.rawDefaultUpperBound
            paramMap[param.alertId]?.apply {
                if (removeIf { it.id == param.id }) {
                    add(param)
                }
            }
        }
    }
}
