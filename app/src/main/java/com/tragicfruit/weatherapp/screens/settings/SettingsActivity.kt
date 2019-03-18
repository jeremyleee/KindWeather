package com.tragicfruit.weatherapp.screens.settings

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.tragicfruit.weatherapp.R
import com.tragicfruit.weatherapp.screens.WActivity
import com.tragicfruit.weatherapp.screens.settings.fragments.list.SettingsListFragment
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : WActivity() {

    override val fragmentContainer = R.id.settingsFragmentContainer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        if (savedInstanceState == null) {
            presentFragment(SettingsListFragment(), false)
        }

        settingsToolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    companion object {
        fun show(context: Context) {
            context.startActivity(Intent(context, SettingsActivity::class.java))
        }
    }

}