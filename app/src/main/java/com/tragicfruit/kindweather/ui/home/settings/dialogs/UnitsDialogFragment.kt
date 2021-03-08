package com.tragicfruit.kindweather.ui.home.settings.dialogs

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.tragicfruit.kindweather.R

class UnitsDialogFragment : DialogFragment() {

    enum class Units {
        METRIC, IMPERIAL
    }

    interface Listener {
        fun onUnitsChanged(units: Units)
    }

    var listener: Listener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val unit = (arguments?.getSerializable(UNIT) as? Units) ?: Units.METRIC

        return activity?.let {
            AlertDialog.Builder(it)
                .setTitle(R.string.settings_units)
                .setSingleChoiceItems(R.array.units, unit.ordinal) { _, which ->
                    listener?.onUnitsChanged(Units.values()[which])
                    dismiss()
                }
                .create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    companion object {
        private const val UNIT = "unit"

        fun newInstance(units: Units, listener: Listener) =
            UnitsDialogFragment().also {
                it.arguments = bundleOf(UNIT to units)
                it.listener = listener
            }
    }
}
