package com.tragicfruit.weatherapp.screens.alert.fragments.detail

import com.tragicfruit.weatherapp.model.WeatherAlert
import io.realm.Realm

class AlertDetailPresenter(override var view: AlertDetailContract.View) : AlertDetailContract.Presenter {

    private lateinit var alert: WeatherAlert

    override fun init(alertId: String?) {
        if (alertId.isNullOrEmpty()) {
            return view.closeScreen()
        }

        val alertFromId = WeatherAlert.fromId(alertId, Realm.getDefaultInstance())
            ?: return view.closeScreen()

        alert = alertFromId
        view.onInitView(alert)
    }

    override fun onToolbarBackClicked() {
        view.closeScreen()
    }

    override fun onAlertEnabled(enabled: Boolean) {
        Realm.getDefaultInstance().executeTransaction {
            WeatherAlert.setEnabled(alert, enabled, it)
        }
    }

}