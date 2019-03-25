package com.tragicfruit.kindweather.screens.home.fragments.forecast

import com.tragicfruit.kindweather.controllers.ForecastIcon
import com.tragicfruit.kindweather.model.ForecastPeriod
import com.tragicfruit.kindweather.model.ForecastType
import com.tragicfruit.kindweather.utils.DisplayUtils
import io.realm.Realm
import io.realm.kotlin.where
import java.util.*
import java.util.concurrent.TimeUnit

class ForecastPresenter(override var view: ForecastContract.View) : ForecastContract.Presenter {

    private val calendar = Calendar.getInstance()

    override fun init(forecastId: String) {
        val forecast = Realm.getDefaultInstance()
            .where<ForecastPeriod>()
            .equalTo("id", forecastId)
            .findFirst()

        forecast?.let {
            calendar.timeInMillis = TimeUnit.SECONDS.toMillis(it.time)

            view.initView(
                DisplayUtils.getDateString(calendar.time),
                ForecastIcon.fromString(it.icon),
                it.getDataForType(ForecastType.TEMP_HIGH)?.getDisplayString(),
                it.getDataForType(ForecastType.TEMP_LOW)?.getDisplayString(),
                it.getDataForType(ForecastType.PRECIP_PROBABILITY)?.getDisplayString()
            )
        }
    }

    override fun onBackClicked() {
        view.closeScreen()
    }

}