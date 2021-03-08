package com.tragicfruit.kindweather.ui.home.settings.dialogs

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.TimePicker
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult

class TimePickerDialogFragment : DialogFragment(), TimePickerDialog.OnTimeSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val initialHour = arguments?.getInt(KEY_HOUR) ?: 0
        val initialMinute = arguments?.getInt(KEY_MINUTE) ?: 0
        return TimePickerDialog(activity, this, initialHour, initialMinute, false)
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        setFragmentResult(
            REQUEST_UPDATE_TIME,
            bundleOf(
                KEY_HOUR to hourOfDay,
                KEY_MINUTE to minute
            )
        )
    }

    companion object {
        const val REQUEST_UPDATE_TIME = "update_time"
        const val KEY_HOUR = "hour"
        const val KEY_MINUTE = "minute"

        fun newInstance(
            initialHour: Int,
            initialMinute: Int
        ) = TimePickerDialogFragment().also {
            it.arguments = bundleOf(
                KEY_HOUR to initialHour,
                KEY_MINUTE to initialMinute
            )
        }
    }
}
