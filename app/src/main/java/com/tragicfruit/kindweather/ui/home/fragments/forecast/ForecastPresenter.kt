package com.tragicfruit.kindweather.ui.home.fragments.forecast

import com.tragicfruit.kindweather.api.ForecastIcon
import com.tragicfruit.kindweather.model.ForecastPeriod
import com.tragicfruit.kindweather.model.ForecastType
import com.tragicfruit.kindweather.utils.DisplayUtils
import com.tragicfruit.kindweather.utils.SharedPrefsHelper
import io.realm.Realm
import io.realm.kotlin.where
import java.util.*

class ForecastPresenter(
    override var view: ForecastContract.View,
    private val sharedPrefsHelper: SharedPrefsHelper
) : ForecastContract.Presenter {

    private val calendar = Calendar.getInstance()

    override fun init(forecastId: String, timeCreatedMillis: Long, color: Int) {
        val forecast = Realm.getDefaultInstance()
            .where<ForecastPeriod>()
            .equalTo("id", forecastId)
            .findFirst()

        forecast?.let {
            calendar.timeInMillis = timeCreatedMillis

            val imperialUnits = sharedPrefsHelper.usesImperialUnits()
            view.initView(
                color,
                DisplayUtils.getDateString(calendar.time),
                ForecastIcon.fromString(it.icon),
                it.getDataForType(ForecastType.TEMP_HIGH)?.getDisplayString(imperialUnits),
                it.getDataForType(ForecastType.TEMP_LOW)?.getDisplayString(imperialUnits),
                it.getDataForType(ForecastType.PRECIP_PROBABILITY)?.getDisplayString(imperialUnits)
            )

            view.fetchAddress(forecast.latitude, forecast.longitude)
        }
    }

    override fun onAddressFetched(address: String?) {
        view.showAddress(address)
    }

    override fun onBackClicked() {
        view.closeScreen()
    }

}