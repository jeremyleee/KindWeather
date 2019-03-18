package com.tragicfruit.weatherapp.screens.settings.fragments.list

import com.tragicfruit.weatherapp.utils.SharedPrefsHelper

class SettingsListPresenter(override var view: SettingsListContract.View) : SettingsListContract.Presenter {

    override fun init() {
        view.updateAlertTimeText(SharedPrefsHelper.getAlertHour(), SharedPrefsHelper.getAlertMinute())
        view.updateUnitsText(SharedPrefsHelper.usesMetricUnits())
    }

    override fun onAlertTimeClicked() {
        view.showAlertTimeDialog(
            SharedPrefsHelper.getAlertHour(),
            SharedPrefsHelper.getAlertMinute())
    }

    override fun onAlertTimeChanged(hourOfDay: Int, minute: Int) {
        SharedPrefsHelper.setAlertHour(hourOfDay)
        SharedPrefsHelper.setAlertMinute(minute)
        view.updateAlertTimeText(hourOfDay, minute)

        // Restart alert service for new alert time
        view.restartAlertService()
    }

    override fun onUnitsClicked() {
        view.showChangeUnitsDialog(SharedPrefsHelper.usesMetricUnits())
    }

    override fun onUnitsChanged(metric: Boolean) {
        SharedPrefsHelper.setUsesMetricUnits(metric)
        view.updateUnitsText(metric)
    }

    override fun onDarkSkyDisclaimerClicked() {
        view.openWebPage("https://darksky.net/poweredby/")
    }

}