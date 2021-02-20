package com.tragicfruit.kindweather.ui.welcome.allowlocation

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.tragicfruit.kindweather.databinding.FragmentAllowLocationBinding
import com.tragicfruit.kindweather.ui.WFragment
import com.tragicfruit.kindweather.utils.PermissionHelper

class AllowLocationFragment : WFragment() {

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
            requestLocationPermission(it.context)
        }
    }

    private fun requestLocationPermission(context: Context) {
        if (!PermissionHelper.hasFineLocationPermission(context)) {
            requestPermissions(PermissionHelper.FINE_LOCATION, REQUEST_LOCATION_PERMISSION)
        } else {
            setResult(false)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_LOCATION_PERMISSION -> {
                val permissionsGranted = grantResults.all { it == PackageManager.PERMISSION_GRANTED }
                setResult(permissionsGranted)
            }
        }
    }

    private fun setResult(permissionsGranted: Boolean) {
        setFragmentResult(
            REQUEST_LOCATION,
            bundleOf(KEY_PERMISSIONS_GRANTED to permissionsGranted)
        )
    }

    companion object {
        const val REQUEST_LOCATION = "request_location"
        const val KEY_PERMISSIONS_GRANTED = "permissions_granted"

        private const val REQUEST_LOCATION_PERMISSION = 700
    }

}