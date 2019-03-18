package com.tragicfruit.weatherapp.screens.alertdetail

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import com.tragicfruit.weatherapp.R
import com.tragicfruit.weatherapp.screens.WActivity
import com.tragicfruit.weatherapp.screens.alertdetail.fragments.detail.AlertDetailFragment

class AlertDetailActivity : WActivity() {

    override val fragmentContainer = R.id.alertDetailFragmentContainer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alert_detail)

        if (savedInstanceState == null) {
            val alertId = intent.extras?.getString(SELECTED_ALERT_ID) ?: ""
            presentFragment(AlertDetailFragment.newInstance(alertId))
        }
    }

    companion object {
        private const val SELECTED_ALERT_ID = "selected-alert-id"

        fun show(activity: Activity, alertId: String, sharedElementView: View? = null) {
            val options = sharedElementView?.let {
                ActivityOptionsCompat.makeSceneTransitionAnimation(activity, it, it.transitionName)
            }

            activity.startActivity(Intent(activity, AlertDetailActivity::class.java).apply {
                putExtra(SELECTED_ALERT_ID, alertId)
            }, options?.toBundle())
        }
    }

}