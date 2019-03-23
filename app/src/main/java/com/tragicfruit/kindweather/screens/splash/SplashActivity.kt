package com.tragicfruit.kindweather.screens.splash

import android.os.Bundle
import com.tragicfruit.kindweather.R
import com.tragicfruit.kindweather.screens.WActivity
import com.tragicfruit.kindweather.screens.home.HomeActivity
import com.tragicfruit.kindweather.screens.welcome.WelcomeActivity
import com.tragicfruit.kindweather.utils.SharedPrefsHelper

class SplashActivity : WActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        if (SharedPrefsHelper.isOnboardingCompleted()) {
            HomeActivity.show(this)
        } else {
            WelcomeActivity.show(this)
        }
        finish()
    }

}