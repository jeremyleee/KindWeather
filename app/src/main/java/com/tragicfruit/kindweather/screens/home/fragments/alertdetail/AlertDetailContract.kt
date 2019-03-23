package com.tragicfruit.kindweather.screens.home.fragments.alertdetail

import com.tragicfruit.kindweather.model.WeatherAlert
import com.tragicfruit.kindweather.model.WeatherAlertParam
import com.tragicfruit.kindweather.screens.WPresenter
import com.tragicfruit.kindweather.screens.WView

interface AlertDetailContract {

    interface View : WView {
        fun initView(alert: WeatherAlert)
        fun closeScreen()
        fun refreshParamList(alert: WeatherAlert)
        fun setResetButtonEnabled(enabled: Boolean)
    }

    interface Presenter : WPresenter<View> {
        fun init(alertId: Int)
        fun onToolbarBackClicked()
        fun onAlertEnabled(enabled: Boolean)
        fun onLowerBoundChanged(param: WeatherAlertParam, value: Double?)
        fun onUpperBoundChanged(param: WeatherAlertParam, value: Double?)
        fun onResetToDefaultClicked()
    }

}