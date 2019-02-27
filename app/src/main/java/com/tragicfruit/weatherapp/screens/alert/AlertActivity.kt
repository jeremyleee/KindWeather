package com.tragicfruit.weatherapp.screens.alert

import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.tragicfruit.weatherapp.R
import com.tragicfruit.weatherapp.screens.WActivity

class AlertActivity : WActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alert)

        val navHost = NavHostFragment.create(R.navigation.navigation_alert)
        supportFragmentManager.beginTransaction()
            .replace(R.id.alertFragmentContainer, navHost)
            .setPrimaryNavigationFragment(navHost)
            .commit()
    }
}
