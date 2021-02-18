package com.tragicfruit.kindweather.screens.welcome.fragments.allowlocation

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.tragicfruit.kindweather.R
import com.tragicfruit.kindweather.screens.WFragment
import com.tragicfruit.kindweather.utils.PermissionHelper
import kotlinx.android.synthetic.main.fragment_allow_location.*

class AllowLocationFragment : WFragment(), AllowLocationContract.View {

    private val presenter = AllowLocationPresenter(this)
    private var callback: AllowLocationContract.Callback? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_allow_location, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        allowLocationButton.setOnClickListener {
            presenter.onAllowClicked()
        }
    }

    override fun requestLocationPermission() {
        context?.let {
            if (!PermissionHelper.hasFineLocationPermission(it)) {
                requestPermissions(PermissionHelper.FINE_LOCATION, REQUEST_LOCATION_PERMISSION)
            } else {
                presenter.onPermissionAllowed()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_LOCATION_PERMISSION -> {
                if (grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                    presenter.onPermissionAllowed()
                } else {
                    presenter.onPermissionDenied()
                }
            }
        }
    }

    override fun showNextScreen() {
        callback?.onLocationPermissionGranted()
    }

    override fun showPermissionsRequiredError() {
        Toast.makeText(context, R.string.allow_location_permission_error, Toast.LENGTH_LONG).show()
    }

    companion object {
        private const val REQUEST_LOCATION_PERMISSION = 700

        fun newInstance(callback: AllowLocationContract.Callback): AllowLocationFragment {
            return AllowLocationFragment().also {
                it.callback = callback
            }
        }
    }

}