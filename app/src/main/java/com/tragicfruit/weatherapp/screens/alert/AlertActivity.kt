package com.tragicfruit.weatherapp.screens.alert

import android.content.Context
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
        fun show(context: Context) {
            context.startActivity(Intent(context, AlertActivity::class.java))
        }
    }
}
