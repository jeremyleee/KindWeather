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
    }

    companion object {
        fun show(context: Context) {
            context.startActivity(Intent(context, HomeActivity::class.java))
        }
    }
}
