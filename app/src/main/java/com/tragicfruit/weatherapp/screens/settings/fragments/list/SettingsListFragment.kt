package com.tragicfruit.weatherapp.screens.settings.fragments.list

import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import com.tragicfruit.weatherapp.R
import com.tragicfruit.weatherapp.controllers.AlertController
import com.tragicfruit.weatherapp.screens.WFragment
import kotlinx.android.synthetic.main.fragment_settings_list.*

class SettingsListFragment : WFragment(), SettingsListContract.View, TimePickerDialog.OnTimeSetListener {

    private val presenter = SettingsListPresenter(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_settings_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.init()

        settingsListAlertTime.setTitle(R.string.settings_list_alert_time)
        settingsListAlertTime.setOnClickListener {
            presenter.onAlertTimeClicked()
        }
    }

    override fun showAlertTimeDialog(initialAlertHour: Int, initialAlertMinute: Int) {
        TimePickerDialog(context,
            this,
            initialAlertHour,
            initialAlertMinute,
            false)
            .show()
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        presenter.onAlertTimeChanged(hourOfDay, minute)
    }

    override fun updateAlertTimeText(alertTime: String) {
        settingsListAlertTime.setSubtitle(alertTime)
    }

    override fun restartAlertService() {
        context?.let {
            AlertController.scheduleDailyAlert(it)
        }
    }

}