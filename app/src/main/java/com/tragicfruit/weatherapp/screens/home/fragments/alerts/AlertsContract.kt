package com.tragicfruit.weatherapp.screens.home.fragments.alerts

import com.tragicfruit.weatherapp.model.WeatherAlert
import com.tragicfruit.weatherapp.screens.WPresenter
import com.tragicfruit.weatherapp.screens.WView

interface AlertsContract {

    interface View : WView {
        fun showAlertDetailScreen(alert: WeatherAlert, position: Int)
        fun refreshList()
        fun requestLocationPermission()
    }

    interface Presenter : WPresenter<View> {
        fun onAlertClicked(alert: WeatherAlert, position: Int)
        fun onAllowLocationClicked()
    }

}