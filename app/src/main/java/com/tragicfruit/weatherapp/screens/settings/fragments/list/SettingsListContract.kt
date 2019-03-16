package com.tragicfruit.weatherapp.screens.settings.fragments.list

import com.tragicfruit.weatherapp.screens.WPresenter
import com.tragicfruit.weatherapp.screens.WView

interface SettingsListContract {

    interface View : WView {
        fun showAlertTimeDialog(initialAlertHour: Int, initialAlertMinute: Int)
        fun updateAlertTimeText(alertTime: String)
        fun restartAlertService()
        fun openWebPage(url: String)
    }

    interface Presenter : WPresenter<View> {
        fun init()
        fun onAlertTimeClicked()
        fun onAlertTimeChanged(hourOfDay: Int, minute: Int)
        fun onDarkSkyDisclaimerClicked()
    }

}