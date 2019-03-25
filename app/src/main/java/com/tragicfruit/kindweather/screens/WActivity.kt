package com.tragicfruit.kindweather.screens

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes
import com.microsoft.appcenter.distribute.Distribute
import com.tragicfruit.kindweather.BuildConfig
import com.tragicfruit.kindweather.R

open class WActivity : AppCompatActivity() {

    @IdRes
    protected open val fragmentContainer: Int = android.R.id.content

    @ColorRes
    open var statusBarColor = R.color.white
    open var lightStatusBar = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        applyStatusBarColorRes(statusBarColor, lightStatusBar)

        AppCenter.start(application, BuildConfig.APP_CENTER,
            Analytics::class.java,
            Crashes::class.java,
            Distribute::class.java)
    }

    fun presentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(fragmentContainer, fragment)
            .commit()
    }

    fun applyStatusBarColor(@ColorInt color: Int, lightStatusBar: Boolean) {
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = color

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = if (lightStatusBar) {
                window.decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            } else {
                window.decorView.systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
            }
        }
    }

    fun applyStatusBarColorRes(@ColorRes colorRes: Int, lightStatusBar: Boolean) {
        applyStatusBarColor(ContextCompat.getColor(this, colorRes), lightStatusBar)
    }

}