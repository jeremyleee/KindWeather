package com.tragicfruit.kindweather.ui.welcome.fragments.allowlocation

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.tragicfruit.kindweather.R
import com.tragicfruit.kindweather.databinding.FragmentAllowLocationBinding
import com.tragicfruit.kindweather.ui.WFragment
import com.tragicfruit.kindweather.utils.PermissionHelper

class AllowLocationFragment : WFragment(), AllowLocationContract.View {

    private val presenter = AllowLocationPresenter(this)
    private var callback: AllowLocationContract.Callback? = null

    private var _binding: FragmentAllowLocationBinding? = null
    private val binding get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllowLocationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.allowButton.setOnClickListener {
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