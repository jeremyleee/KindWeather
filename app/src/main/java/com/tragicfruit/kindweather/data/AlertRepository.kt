package com.tragicfruit.kindweather.data

import com.tragicfruit.kindweather.data.db.dao.AlertDao
import com.tragicfruit.kindweather.data.db.dao.AlertParamDao
import com.tragicfruit.kindweather.data.model.*
import com.tragicfruit.kindweather.utils.SharedPrefsHelper
import kotlinx.coroutines.flow.Flow
import java.util.*
import javax.inject.Inject

class AlertRepository @Inject constructor(
    private val alertDao: AlertDao,
    private val paramDao: AlertParamDao,
    private val sharedPrefsHelper: SharedPrefsHelper
) {

    suspend fun createAlert(priority: Int, type: WeatherAlertType): WeatherAlert {
        return WeatherAlert(
            id = UUID.randomUUID().toString(),
            alertType = type,
            priority = priority
        ).also {
            alertDao.insert(it)
        }
    }

    suspend fun createParam(
        alert: WeatherAlert,
        type: ForecastDataType,
        rawDefaultLowerBound: Double?,
        rawDefaultUpperBound: Double?,
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
            paramDao.insert(it)
        }
    }

    suspend fun findParamsForAlert(alertId: String): WeatherAlertWithParams {
        return alertDao.loadAlertWithParams(alertId)
    }

    fun getAllAlerts(): Flow<List<WeatherAlert>> {
        return alertDao.loadAll()
    }

    suspend fun setAlertEnabled(alert: WeatherAlert, enabled: Boolean) {
        alert.enabled = enabled
        alertDao.update(alert)
    }

    suspend fun getAlertCount(): Int {
        return alertDao.loadCount()
    }

    suspend fun findAlertMatchingForecast(forecast: ForecastPeriod): WeatherAlert? {
        return alertDao.loadAlertMatchingForecast(forecast.id)
    }

    suspend fun setParamLowerBound(param: WeatherAlertParam, lowerBound: Double?) {
        param.setLowerBound(lowerBound, sharedPrefsHelper.usesImperialUnits())
        paramDao.update(param)
    }

    suspend fun setParamUpperBound(param: WeatherAlertParam, upperBound: Double?) {
        param.setUpperBound(upperBound, sharedPrefsHelper.usesImperialUnits())
        paramDao.update(param)
    }

    suspend fun resetParamsToDefault(params: List<WeatherAlertParam>) {
        params.forEach {
            it.rawLowerBound = it.rawDefaultLowerBound
            it.rawUpperBound = it.rawDefaultUpperBound
        }
        paramDao.updateAll(*params.toTypedArray())
    }
}