package com.tragicfruit.kindweather.ui.home.settings

import com.tragicfruit.kindweather.utils.SharedPrefsHelper

class SettingsPresenter(
    override var view: SettingsContract.View,
    private val sharedPrefsHelper: SharedPrefsHelper
) : SettingsContract.Presenter {

    override fun init() {
        view.updateAlertTimeText(sharedPrefsHelper.getAlertHour(), sharedPrefsHelper.getAlertMinute())
        view.updateUnitsText(sharedPrefsHelper.usesImperialUnits())
    }

    override fun onAlertTimeClicked() {
        view.showAlertTimeDialog(
            sharedPrefsHelper.getAlertHour(),
            sharedPrefsHelper.getAlertMinute())
    }

    override fun onAlertTimeChanged(hourOfDay: Int, minute: Int) {
        sharedPrefsHelper.setAlertHour(hourOfDay)
        sharedPrefsHelper.setAlertMinute(minute)
        view.updateAlertTimeText(hourOfDay, minute)

        // Restart alert service for new alert time
        view.restartAlertService()
    }

    override fun onUnitsClicked() {
        view.showChangeUnitsDialog(sharedPrefsHelper.usesImperialUnits())
    }

    override fun onUnitsChanged(imperial: Boolean) {
        sharedPrefsHelper.setUsesImperialUnits(imperial)
        view.updateUnitsText(imperial)
    }

    override fun onDarkSkyDisclaimerClicked() {
        view.openWebPage("https://darksky.net/poweredby/")
    }
}