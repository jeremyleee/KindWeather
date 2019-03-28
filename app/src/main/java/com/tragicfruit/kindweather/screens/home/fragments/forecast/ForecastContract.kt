package com.tragicfruit.kindweather.screens.home.fragments.forecast

import com.tragicfruit.kindweather.controllers.ForecastIcon
import com.tragicfruit.kindweather.screens.WPresenter
import com.tragicfruit.kindweather.screens.WView

interface ForecastContract {

    interface View : WView {
        fun initView(color: Int, dateString: String, icon: ForecastIcon,
                     highTempString: String?, lowTempString: String?, precipString: String?)
        fun fetchAddress(latitude: Double, longitude: Double)
        fun showAddress(address: String?)
        fun closeScreen()
    }

    interface Presenter: WPresenter<View> {
        fun init(forecastId: String, timeCreatedMillis: Long, color: Int)
        fun onAddressFetched(address: String?)
        fun onBackClicked()
    }

}