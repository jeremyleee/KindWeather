package com.tragicfruit.kindweather.screens.welcome

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.tragicfruit.kindweather.R
import com.tragicfruit.kindweather.controllers.AlertController
import com.tragicfruit.kindweather.controllers.FetchForecastWorker
import com.tragicfruit.kindweather.screens.WActivity
import com.tragicfruit.kindweather.screens.home.HomeActivity
import com.tragicfruit.kindweather.screens.welcome.fragments.allowlocation.AllowLocationContract
import com.tragicfruit.kindweather.screens.welcome.fragments.allowlocation.AllowLocationFragment
import com.tragicfruit.kindweather.utils.SharedPrefsHelper

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