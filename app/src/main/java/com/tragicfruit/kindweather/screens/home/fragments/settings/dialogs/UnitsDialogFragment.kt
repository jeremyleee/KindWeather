package com.tragicfruit.kindweather.screens.home.fragments.settings.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.tragicfruit.kindweather.R

class UnitsDialogFragment : DialogFragment() {

    enum class Units {
        METRIC, IMPERIAL
    }

    var listener: Listener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val unit = (arguments?.getSerializable(UNIT) as? Units) ?: Units.METRIC

        return AlertDialog.Builder(activity)
            .setTitle(R.string.settings_units)
            .setSingleChoiceItems(R.array.units, unit.ordinal) { dialog, which ->
                listener?.onUnitsChanged(Units.values()[which])
                dismiss()
            }
            .create()
    }

    companion object {
        private const val UNIT = "unit"

        fun newInstance(units: Units, listener: Listener) =
            UnitsDialogFragment().also {
                it.arguments = Bundle().apply {
                    putSerializable(UNIT, units)
                }
                it.listener = listener
            }
    }

    interface Listener {
        fun onUnitsChanged(units: Units)
    }

}