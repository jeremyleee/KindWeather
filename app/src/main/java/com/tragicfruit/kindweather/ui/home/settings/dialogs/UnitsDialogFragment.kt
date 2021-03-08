package com.tragicfruit.kindweather.ui.home.settings.dialogs

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.tragicfruit.kindweather.R

class UnitsDialogFragment : DialogFragment() {

    enum class Units {
        METRIC, IMPERIAL
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val unit = (arguments?.getSerializable(KEY_UNIT) as? Units) ?: Units.METRIC

        return activity?.let {
            AlertDialog.Builder(it)
                .setTitle(R.string.settings_units)
                .setSingleChoiceItems(R.array.units, unit.ordinal) { _, which ->
                    onUnitsChanged(Units.values()[which])
                    dismiss()
                }
                .create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    private fun onUnitsChanged(units: Units) {
        setFragmentResult(REQUEST_UPDATE_UNITS, bundleOf(KEY_UNIT to units))
    }

    companion object {
        const val REQUEST_UPDATE_UNITS = "update_units"
        const val KEY_UNIT = "unit"

        fun newInstance(units: Units) =
            UnitsDialogFragment().also {
                it.arguments = bundleOf(KEY_UNIT to units)
            }
    }
}
