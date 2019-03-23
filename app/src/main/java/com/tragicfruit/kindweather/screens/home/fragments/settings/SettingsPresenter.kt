package com.tragicfruit.kindweather.screens.home.fragments.settings

import com.tragicfruit.kindweather.utils.SharedPrefsHelper

class SettingsPresenter(override var view: SettingsContract.View) :
    SettingsContract.Presenter {

    override fun init() {
        view.updateAlertTimeText(SharedPrefsHelper.getAlertHour(), SharedPrefsHelper.getAlertMinute())
        view.updateUnitsText(SharedPrefsHelper.usesImperialUnits())
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
        view.showChangeUnitsDialog(SharedPrefsHelper.usesImperialUnits())
    }

    override fun onUnitsChanged(imperial: Boolean) {
        SharedPrefsHelper.setUsesImperialUnits(imperial)
        view.updateUnitsText(imperial)
    }

    override fun onDarkSkyDisclaimerClicked() {
        view.openWebPage("https://darksky.net/poweredby/")
    }

}