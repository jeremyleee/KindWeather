package com.tragicfruit.weatherapp.screens.welcome

import android.os.Bundle
import com.tragicfruit.weatherapp.R
import com.tragicfruit.weatherapp.controllers.FetchForecastWorker
import com.tragicfruit.weatherapp.screens.WActivity
import com.tragicfruit.weatherapp.screens.alert.AlertActivity
import com.tragicfruit.weatherapp.screens.welcome.fragments.allowlocation.AllowLocationContract
import com.tragicfruit.weatherapp.screens.welcome.fragments.allowlocation.AllowLocationFragment

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
        FetchForecastWorker.enqueueWork()

        // Finish onboarding
        AlertActivity.present(this)
        finish()
    }

}