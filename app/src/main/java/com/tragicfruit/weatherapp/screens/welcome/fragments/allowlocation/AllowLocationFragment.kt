package com.tragicfruit.weatherapp.screens.welcome.fragments.allowlocation

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tragicfruit.weatherapp.R
import com.tragicfruit.weatherapp.screens.WFragment
import com.tragicfruit.weatherapp.utils.PermissionHelper
import kotlinx.android.synthetic.main.fragment_allow_location.*

class AllowLocationFragment : WFragment(), AllowLocationContract.View {

    private val presenter = AllowLocationPresenter(this)
    private var callback: AllowLocationContract.Callback? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_allow_location, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        allowLocationButton.setOnClickListener {
            presenter.onAllowClicked()
        }
    }

    override fun requestLocationPermission() {
        activity?.let {
            if (!PermissionHelper.hasPermission(it, Manifest.permission.ACCESS_FINE_LOCATION)) {
                requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_LOCATION_PERMISSION)
            } else {
                presenter.onPermissionAllowed()
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_LOCATION_PERMISSION -> {
                if (grantResults.firstOrNull() == PackageManager.PERMISSION_GRANTED) {
                    presenter.onPermissionAllowed()
                }
            }
        }
    }

    override fun showNextScreen() {
        callback?.onLocationPermissionGranted()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        callback = context as? AllowLocationContract.Callback
    }

    override fun onDetach() {
        callback = null
        super.onDetach()
    }

    companion object {
        private const val REQUEST_LOCATION_PERMISSION = 700
    }

}