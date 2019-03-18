package com.tragicfruit.weatherapp.screens.settings.fragments.list.dialogs

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
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

        fun newInstance(initialHour: Int, initialMinute: Int, listener: TimePickerDialog.OnTimeSetListener) =
            TimePickerDialogFragment().also {
                it.arguments = Bundle().apply {
                    putInt(INITIAL_HOUR, initialHour)
                    putInt(INITIAL_MINUTE, initialMinute)
                }
                it.listener = listener
            }
    }

}