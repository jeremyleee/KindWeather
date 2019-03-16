package com.tragicfruit.weatherapp.screens.welcome

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.tragicfruit.weatherapp.R
import com.tragicfruit.weatherapp.controllers.AlertController
import com.tragicfruit.weatherapp.controllers.FetchForecastWorker
import com.tragicfruit.weatherapp.screens.WActivity
import com.tragicfruit.weatherapp.screens.alert.AlertActivity
import com.tragicfruit.weatherapp.screens.welcome.fragments.allowlocation.AllowLocationContract
import com.tragicfruit.weatherapp.screens.welcome.fragments.allowlocation.AllowLocationFragment
import com.tragicfruit.weatherapp.utils.SharedPrefsHelper

class WelcomeActivity : WActivity(), AllowLocationContract.Callback {

    override val fragmentContainer = R.id.welcomeFragmentContainer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        if (savedInstanceState == null) {
            presentFragment(AllowLocationFragment(), false)
        }
    }

    override fun onLocationPermissionGranted() {
        SharedPrefsHelper.setOnboardingCompleted(true)
        FetchForecastWorker.enqueueWork()
        AlertController.scheduleDailyAlert(this)

        // Finish onboarding
        AlertActivity.show(this)
        finish()
    }

    companion object {
        fun show(activity: Activity) {
            activity.startActivity(Intent(activity, WelcomeActivity::class.java))
        }
    }

}