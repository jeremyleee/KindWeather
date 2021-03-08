package com.tragicfruit.kindweather.ui.home.settings.dialogs

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment

class TimePickerDialogFragment : DialogFragment() {

    lateinit var listener: TimePickerDialog.OnTimeSetListener

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val initialHour = arguments?.getInt(INITIAL_HOUR) ?: 0
        val initialMinute = arguments?.getInt(INITIAL_MINUTE) ?: 0
        return TimePickerDialog(activity, listener, initialHour, initialMinute, false)
    }

    companion object {
        private const val INITIAL_HOUR = "hour"
        private const val INITIAL_MINUTE = "minute"

        fun newInstance(
            initialHour: Int,
            initialMinute: Int,
            listener: TimePickerDialog.OnTimeSetListener
        ) =
            TimePickerDialogFragment().also {
                it.arguments = bundleOf(
                    INITIAL_HOUR to initialHour,
                    INITIAL_MINUTE to initialMinute
                )
                it.listener = listener
            }
    }
}
