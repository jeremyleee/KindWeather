package com.tragicfruit.weatherapp.screens.alert.fragments.list

import com.tragicfruit.weatherapp.model.WeatherAlert

class AlertListPresenter(override var view: AlertListContract.View) : AlertListContract.Presenter {

    override fun onAlertClicked(alert: WeatherAlert, position: Int) {
        view.showAlertDetailScreen(alert, position)
    }

    override fun onAllowLocationClicked() {
        view.requestLocationPermission()
    }

    override fun onSettingsClicked() {
        view.showSettingsScreen()
    }

    override fun resume() {
        view.refreshList()
    }

}