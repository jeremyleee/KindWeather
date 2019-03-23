package com.tragicfruit.kindweather.screens.home.fragments.alertlist

import com.tragicfruit.kindweather.model.WeatherAlert
import com.tragicfruit.kindweather.screens.WPresenter
import com.tragicfruit.kindweather.screens.WView

interface AlertListContract {

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