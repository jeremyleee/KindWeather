package com.tragicfruit.weatherapp.screens.alert.fragments.detail

import com.tragicfruit.weatherapp.model.WeatherAlert
import com.tragicfruit.weatherapp.screens.WPresenter
import com.tragicfruit.weatherapp.screens.WView

interface AlertDetailContract {

    interface View : WView {
        fun onInitView(alert: WeatherAlert)
        fun closeScreen()
    }

    interface Presenter : WPresenter<View> {
        fun init(alertId: String?)
        fun onToolbarBackClicked()
        fun onAlertEnabled(enabled: Boolean)
    }

}