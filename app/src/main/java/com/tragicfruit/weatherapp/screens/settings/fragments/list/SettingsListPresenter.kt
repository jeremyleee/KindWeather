package com.tragicfruit.weatherapp.screens.settings.fragments.list

import com.tragicfruit.weatherapp.utils.SharedPrefsHelper
import java.text.DateFormat
import java.util.*

class SettingsListPresenter(override var view: SettingsListContract.View) : SettingsListContract.Presenter {

    private val calendar = Calendar.getInstance()

    override fun init() {
        view.updateAlertTimeText(getAlertTimeString())
    }

    override fun onAlertTimeClicked() {
        view.showAlertTimeDialog(
            SharedPrefsHelper.getAlertHour(),
            SharedPrefsHelper.getAlertMinute())
    }

    override fun onAlertTimeChanged(hourOfDay: Int, minute: Int) {
        SharedPrefsHelper.setAlertHour(hourOfDay)
        SharedPrefsHelper.setAlertMinute(minute)

        view.updateAlertTimeText(getAlertTimeString())

        // Restart alert service for new alert time
        view.restartAlertService()
    }

    private fun getAlertTimeString(): String {
        calendar.set(Calendar.HOUR_OF_DAY, SharedPrefsHelper.getAlertHour())
        calendar.set(Calendar.MINUTE, SharedPrefsHelper.getAlertMinute())
        calendar.set(Calendar.SECOND, 0)

        val timeFormatter = DateFormat.getTimeInstance(DateFormat.SHORT)
        return timeFormatter.format(calendar.time)
    }

}