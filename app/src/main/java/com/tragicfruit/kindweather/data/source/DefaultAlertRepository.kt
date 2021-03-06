package com.tragicfruit.kindweather.data.source

import com.tragicfruit.kindweather.data.ForecastDataType
import com.tragicfruit.kindweather.data.ForecastPeriod
import com.tragicfruit.kindweather.data.WeatherAlert
import com.tragicfruit.kindweather.data.WeatherAlertParam
import com.tragicfruit.kindweather.data.WeatherAlertType
import com.tragicfruit.kindweather.data.WeatherAlertWithParams
import com.tragicfruit.kindweather.data.source.local.AlertDao
import com.tragicfruit.kindweather.data.source.local.AlertParamDao
import com.tragicfruit.kindweather.di.IoDispatcher
import com.tragicfruit.kindweather.util.SharedPrefsHelper
import java.util.UUID
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class DefaultAlertRepository @Inject constructor(
    private val alertDao: AlertDao,
    private val paramDao: AlertParamDao,
    private val sharedPrefsHelper: SharedPrefsHelper,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : AlertRepository {

    override suspend fun createAlert(priority: Int, type: WeatherAlertType): WeatherAlert {
        return withContext(ioDispatcher) {
            WeatherAlert(
                id = UUID.randomUUID().toString(),
                alertType = type,
                priority = priority
            ).also {
                alertDao.insert(it)
            }
        }
    }

    override suspend fun createParam(
        alert: WeatherAlert,
        type: ForecastDataType,
        rawDefaultLowerBound: Double?,
        rawDefaultUpperBound: Double?,
    ): WeatherAlertParam {
        return withContext(ioDispatcher) {
            WeatherAlertParam(
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
    }

    override suspend fun findParamsForAlert(alertId: String): WeatherAlertWithParams {
        return withContext(ioDispatcher) {
            alertDao.loadAlertWithParams(alertId)
        }
    }

    override fun getAllAlerts(): Flow<List<WeatherAlert>> {
        return alertDao.loadAll()
    }

    override suspend fun setAlertEnabled(alert: WeatherAlert, enabled: Boolean) {
        withContext(ioDispatcher) {
            alert.enabled = enabled
            alertDao.update(alert)
        }
    }

    override suspend fun getAlertCount(): Int {
        return withContext(ioDispatcher) {
            alertDao.loadCount()
        }
    }

    override suspend fun findAlertMatchingForecast(forecast: ForecastPeriod): WeatherAlert? {
        return withContext(ioDispatcher) {
            alertDao.loadAlertMatchingForecast(forecast.id)
        }
    }

    override suspend fun setParamLowerBound(param: WeatherAlertParam, lowerBound: Double?) {
        return withContext(ioDispatcher) {
            param.setLowerBound(lowerBound, sharedPrefsHelper.usesImperialUnits())
            paramDao.update(param)
        }
    }

    override suspend fun setParamUpperBound(param: WeatherAlertParam, upperBound: Double?) {
        return withContext(ioDispatcher) {
            param.setUpperBound(upperBound, sharedPrefsHelper.usesImperialUnits())
            paramDao.update(param)
        }
    }

    override suspend fun resetParamsToDefault(params: List<WeatherAlertParam>) {
        return withContext(ioDispatcher) {
            params.forEach {
                it.rawLowerBound = it.rawDefaultLowerBound
                it.rawUpperBound = it.rawDefaultUpperBound
            }
            paramDao.updateAll(*params.toTypedArray())
        }
    }
}
