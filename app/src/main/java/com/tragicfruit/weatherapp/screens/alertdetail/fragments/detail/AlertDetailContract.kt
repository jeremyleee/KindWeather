package com.tragicfruit.weatherapp.screens.alertdetail.fragments.detail

import com.tragicfruit.weatherapp.model.WeatherAlert
import com.tragicfruit.weatherapp.model.WeatherAlertParam
import com.tragicfruit.weatherapp.screens.WPresenter
import com.tragicfruit.weatherapp.screens.WView

interface AlertDetailContract {

    interface View : WView {
        fun onInitView(alert: WeatherAlert)
        fun closeScreen()
        fun refreshParamList(alert: WeatherAlert)
        fun setResetButtonEnabled(enabled: Boolean)
    }

    interface Presenter : WPresenter<View> {
        fun init(alertId: String?)
        fun onToolbarBackClicked()
        fun onAlertEnabled(enabled: Boolean)
        fun onLowerBoundChanged(param: WeatherAlertParam, value: Double?)
        fun onUpperBoundChanged(param: WeatherAlertParam, value: Double?)
        fun onResetToDefaultClicked()
    }

}