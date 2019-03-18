package com.tragicfruit.weatherapp.screens

import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes
import com.microsoft.appcenter.distribute.Distribute
import com.tragicfruit.weatherapp.BuildConfig

open class WActivity : AppCompatActivity() {

    @IdRes
    protected open val fragmentContainer: Int = android.R.id.content

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AppCenter.start(application, BuildConfig.APP_CENTER,
            Analytics::class.java,
            Crashes::class.java,
            Distribute::class.java)
    }

    fun presentFragment(fragment: Fragment, addToBackStack: Boolean = true, sharedElementView: View? = null) {
        supportFragmentManager.beginTransaction()
            .apply {
                if (addToBackStack) {
                    addToBackStack(fragment.tag)
                }

                sharedElementView?.let {
                    addSharedElement(it, it.transitionName)
                }
            }
            .replace(fragmentContainer, fragment)
            .commit()
    }

}