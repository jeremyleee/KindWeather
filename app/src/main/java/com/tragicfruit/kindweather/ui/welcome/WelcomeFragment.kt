package com.tragicfruit.kindweather.ui.welcome

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.tragicfruit.kindweather.R
import com.tragicfruit.kindweather.databinding.FragmentWelcomeBinding
import com.tragicfruit.kindweather.ui.BaseFragment
import com.tragicfruit.kindweather.ui.welcome.allowlocation.AllowLocationFragment
import com.tragicfruit.kindweather.utils.SharedPrefsHelper
import com.tragicfruit.kindweather.utils.controllers.AlertController
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WelcomeFragment : BaseFragment() {

    @Inject lateinit var alertController: AlertController
    @Inject lateinit var sharedPrefsHelper: SharedPrefsHelper

    private var _binding: FragmentWelcomeBinding? = null
    private val binding get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        childFragmentManager.setFragmentResultListener(
            AllowLocationFragment.REQUEST_LOCATION,
            viewLifecycleOwner
        ) { _, bundle ->
            if (bundle.getBoolean(AllowLocationFragment.KEY_PERMISSIONS_GRANTED)) {
                onLocationPermissionGranted(view.context)
            } else {
                onLocationPermissionDenied()
            }
        }

        val adapter = WelcomeAdapter(this)
        binding.viewPager.adapter = adapter
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.pageIndicator.setCurrentPage(position)
            }
        })
        binding.pageIndicator.setPageCount(adapter.itemCount)
    }

    private fun onLocationPermissionGranted(context: Context) {
        sharedPrefsHelper.setOnboardingCompleted(true)
        alertController.scheduleDailyAlert(context)

        // Finish onboarding
        val directions = WelcomeFragmentDirections.actionWelcomeFragmentToHomeFragment(true)
        findNavController().navigate(directions)
    }

    private fun onLocationPermissionDenied() {
        Toast.makeText(context, R.string.allow_location_permission_error, Toast.LENGTH_LONG).show()
    }
}
