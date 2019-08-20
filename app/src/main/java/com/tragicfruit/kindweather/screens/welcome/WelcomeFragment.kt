package com.tragicfruit.kindweather.screens.welcome

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import com.tragicfruit.kindweather.R
import com.tragicfruit.kindweather.controllers.AlertController
import com.tragicfruit.kindweather.controllers.FetchForecastWorker
import com.tragicfruit.kindweather.screens.WFragment
import com.tragicfruit.kindweather.screens.welcome.fragments.allowlocation.AllowLocationContract
import com.tragicfruit.kindweather.utils.SharedPrefsHelper
import kotlinx.android.synthetic.main.fragment_welcome.*

class WelcomeFragment : WFragment(), AllowLocationContract.Callback, ViewPager.OnPageChangeListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_welcome, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = WelcomeAdapter(childFragmentManager, this)
        welcomeViewPager.adapter = adapter
        welcomeViewPager.addOnPageChangeListener(this)
        welcomePageIndicator.setPageCount(adapter.count)
    }

    override fun onPageSelected(position: Int) {
        welcomePageIndicator.setCurrentPage(position)
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