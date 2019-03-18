package com.tragicfruit.weatherapp.screens.alert.fragments.list

import com.tragicfruit.weatherapp.model.WeatherAlert
import com.tragicfruit.weatherapp.screens.WPresenter
import com.tragicfruit.weatherapp.screens.WView

interface AlertListContract {

    interface View : WView {
        fun showAlertDetailScreen(alert: WeatherAlert, position: Int)
        fun refreshList()
        fun requestLocationPermission()
        fun showSettingsScreen()
    }

    interface Presenter : WPresenter<View> {
        fun onAlertClicked(alert: WeatherAlert, position: Int)
        fun onAllowLocationClicked()
        fun onSettingsClicked()
    }

}