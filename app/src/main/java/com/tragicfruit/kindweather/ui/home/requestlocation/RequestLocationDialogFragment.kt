package com.tragicfruit.kindweather.ui.home.requestlocation

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.tragicfruit.kindweather.R
import com.tragicfruit.kindweather.utils.PermissionHelper

class RequestLocationDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            AlertDialog.Builder(it)
                .setTitle(R.string.allow_location_dialog_title)
                .setMessage(
                    getString(
                        R.string.allow_location_dialog_text,
                        it.packageManager.backgroundPermissionOptionLabel
                    )
                )
                .setPositiveButton(android.R.string.ok) { _, _ ->
                    requestPermissions(
                        PermissionHelper.BACKGROUND_LOCATION,
                        REQUEST_LOCATION_PERMISSION
                    )
                }
                .setNegativeButton(android.R.string.cancel, null)
                .create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    companion object {
        const val TAG = "RequestLocationDialogFragment"

        private const val REQUEST_LOCATION_PERMISSION = 800
    }
}
