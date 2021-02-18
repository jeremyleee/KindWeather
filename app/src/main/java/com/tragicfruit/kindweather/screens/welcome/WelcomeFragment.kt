package com.tragicfruit.kindweather.screens.welcome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import com.tragicfruit.kindweather.controllers.AlertController
import com.tragicfruit.kindweather.controllers.FetchForecastWorker
import com.tragicfruit.kindweather.databinding.FragmentWelcomeBinding
import com.tragicfruit.kindweather.screens.WFragment
import com.tragicfruit.kindweather.screens.welcome.fragments.allowlocation.AllowLocationContract
import com.tragicfruit.kindweather.utils.SharedPrefsHelper

class WelcomeFragment : WFragment(), AllowLocationContract.Callback, ViewPager.OnPageChangeListener {

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

        val adapter = WelcomeAdapter(childFragmentManager, this)
        binding.viewPager.adapter = adapter
        binding.viewPager.addOnPageChangeListener(this)
        binding.pageIndicator.setPageCount(adapter.count)
    }

    override fun onPageSelected(position: Int) {
        binding.pageIndicator.setCurrentPage(position)
    }

    override fun onLocationPermissionGranted() {
        SharedPrefsHelper.setOnboardingCompleted(true)
        FetchForecastWorker.enqueueWork()
        context?.let { AlertController.scheduleDailyAlert(it) }

        // Finish onboarding
        val directions = WelcomeFragmentDirections.actionWelcomeFragmentToHomeFragment(true)
        findNavController().navigate(directions)
    }

    override fun onPageScrollStateChanged(state: Int) = Unit
    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) = Unit

}