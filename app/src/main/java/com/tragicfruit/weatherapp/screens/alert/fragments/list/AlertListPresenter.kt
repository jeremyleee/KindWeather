package com.tragicfruit.weatherapp.screens.alert.fragments.list

import com.tragicfruit.weatherapp.model.WeatherAlert

class AlertListPresenter(override var view: AlertListContract.View) : AlertListContract.Presenter {

    override fun onAlertClicked(alert: WeatherAlert) {
        view.showAlertDetailScreen(alert)
    }

    override fun start() {
        view.refreshList()
    }

}