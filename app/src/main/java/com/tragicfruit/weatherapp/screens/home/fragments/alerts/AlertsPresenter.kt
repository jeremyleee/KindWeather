package com.tragicfruit.weatherapp.screens.home.fragments.alerts

import com.tragicfruit.weatherapp.model.WeatherAlert

class AlertsPresenter(override var view: AlertsContract.View) : AlertsContract.Presenter {

    override fun onAlertClicked(alert: WeatherAlert, position: Int) {
        view.showAlertDetailScreen(alert, position)
    }

    override fun onAllowLocationClicked() {
        view.requestLocationPermission()
    }

    override fun resume() {
        view.refreshList()
    }

}