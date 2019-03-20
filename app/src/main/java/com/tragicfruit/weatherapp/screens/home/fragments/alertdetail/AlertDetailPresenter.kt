package com.tragicfruit.weatherapp.screens.home.fragments.alertdetail

import com.tragicfruit.weatherapp.model.WeatherAlert
import com.tragicfruit.weatherapp.model.WeatherAlertParam
import io.realm.Realm

class AlertDetailPresenter(override var view: AlertDetailContract.View) : AlertDetailContract.Presenter {

    private lateinit var alert: WeatherAlert

    override fun init(alertId: Int) {
        val alertFromId = WeatherAlert.fromId(alertId, Realm.getDefaultInstance())
            ?: return view.closeScreen()

        alert = alertFromId
        view.initView(alert)
    }

    override fun onToolbarBackClicked() {
        view.closeScreen()
    }

    override fun onAlertEnabled(enabled: Boolean) {
        Realm.getDefaultInstance().executeTransaction {
            WeatherAlert.setEnabled(alert, enabled, it)
        }
    }

    override fun onLowerBoundChanged(param: WeatherAlertParam, value: Double?) {
        Realm.getDefaultInstance().executeTransaction {
            WeatherAlertParam.setLowerBound(param, value, it)
        }

        view.setResetButtonEnabled(true)
    }

    override fun onUpperBoundChanged(param: WeatherAlertParam, value: Double?) {
        Realm.getDefaultInstance().executeTransaction {
            WeatherAlertParam.setUpperBound(param, value, it)
        }

        view.setResetButtonEnabled(true)
    }

    override fun onResetToDefaultClicked() {
        Realm.getDefaultInstance().executeTransaction { realm ->
            alert.params.forEach { param ->
                WeatherAlertParam.resetToDefault(param, realm)
            }
        }

        view.setResetButtonEnabled(false)
        view.refreshParamList(alert)
    }

}