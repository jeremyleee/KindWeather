package com.tragicfruit.weatherapp.screens.alert

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.tragicfruit.weatherapp.R
import com.tragicfruit.weatherapp.screens.WActivity
import com.tragicfruit.weatherapp.screens.alert.fragments.list.AlertListFragment

class AlertActivity : WActivity() {

    override val fragmentContainer = R.id.alertFragmentContainer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alert)

        if (savedInstanceState == null) {
            presentFragment(AlertListFragment(), false)
        }
    }

    companion object {
        fun present(activity: Activity) {
            activity.startActivity(Intent(activity, AlertActivity::class.java))
        }
    }
}
