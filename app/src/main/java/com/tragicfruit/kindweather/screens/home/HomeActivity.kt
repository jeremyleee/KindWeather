package com.tragicfruit.kindweather.screens.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.tragicfruit.kindweather.R
import com.tragicfruit.kindweather.screens.WActivity
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : WActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val navController = findNavController(R.id.homeNavHostFragment)
        homeBottomNavigation.setupWithNavController(navController)

        // Show the conditions screen first if user has just onboarded
        if (intent.extras?.getBoolean(FROM_ONBOARDING_KEY) == true) {
            navController.navigate(R.id.alertListFragment)
        }
    }

    companion object {
        private const val FROM_ONBOARDING_KEY = "from-onboarding"

        fun show(context: Context, fromOnboarding: Boolean) {
            context.startActivity(Intent(context, HomeActivity::class.java).apply {
                putExtra(FROM_ONBOARDING_KEY, fromOnboarding)
            })
        }
    }
}
