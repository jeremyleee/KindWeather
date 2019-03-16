package com.tragicfruit.weatherapp.screens.splash

import android.os.Bundle
import com.tragicfruit.weatherapp.R
import com.tragicfruit.weatherapp.screens.WActivity
import com.tragicfruit.weatherapp.screens.alert.AlertActivity
import com.tragicfruit.weatherapp.screens.welcome.WelcomeActivity
import com.tragicfruit.weatherapp.utils.SharedPrefsHelper

class SplashActivity : WActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        if (SharedPrefsHelper.isOnboardingCompleted()) {
            AlertActivity.show(this)
        } else {
            WelcomeActivity.show(this)
        }
        finish()
    }

}