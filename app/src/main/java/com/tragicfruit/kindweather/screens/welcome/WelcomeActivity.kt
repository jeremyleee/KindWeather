package com.tragicfruit.kindweather.screens.welcome

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.tragicfruit.kindweather.R
import com.tragicfruit.kindweather.controllers.AlertController
import com.tragicfruit.kindweather.controllers.FetchForecastWorker
import com.tragicfruit.kindweather.screens.WActivity
import com.tragicfruit.kindweather.screens.home.HomeActivity
import com.tragicfruit.kindweather.screens.welcome.fragments.allowlocation.AllowLocationContract
import com.tragicfruit.kindweather.utils.SharedPrefsHelper
import kotlinx.android.synthetic.main.activity_welcome.*

class WelcomeActivity : WActivity(), AllowLocationContract.Callback, ViewPager.OnPageChangeListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        val adapter = WelcomeAdapter(supportFragmentManager)
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
        AlertController.scheduleDailyAlert(this)

        // Finish onboarding
        HomeActivity.show(this, true)
        finish()
    }

    override fun onPageScrollStateChanged(state: Int) = Unit
    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) = Unit

    companion object {
        fun show(context: Context) {
            context.startActivity(Intent(context, WelcomeActivity::class.java))
        }
    }

}