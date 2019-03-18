package com.tragicfruit.weatherapp.screens.welcome

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.tragicfruit.weatherapp.R
import com.tragicfruit.weatherapp.controllers.AlertController
import com.tragicfruit.weatherapp.controllers.FetchForecastWorker
import com.tragicfruit.weatherapp.screens.WActivity
import com.tragicfruit.weatherapp.screens.home.HomeActivity
import com.tragicfruit.weatherapp.screens.welcome.fragments.allowlocation.AllowLocationContract
import com.tragicfruit.weatherapp.screens.welcome.fragments.allowlocation.AllowLocationFragment
import com.tragicfruit.weatherapp.utils.SharedPrefsHelper

class WelcomeActivity : WActivity(), AllowLocationContract.Callback {

    override val fragmentContainer = R.id.welcomeFragmentContainer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        if (savedInstanceState == null) {
            presentFragment(AllowLocationFragment())
        }
    }

    override fun onLocationPermissionGranted() {
        SharedPrefsHelper.setOnboardingCompleted(true)
        FetchForecastWorker.enqueueWork()
        AlertController.scheduleDailyAlert(this)

        // Finish onboarding
        HomeActivity.show(this)
        finish()
    }

    companion object {
        fun show(context: Context) {
            context.startActivity(Intent(context, WelcomeActivity::class.java))
        }
    }

}