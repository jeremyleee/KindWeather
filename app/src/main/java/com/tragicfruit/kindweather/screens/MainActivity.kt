package com.tragicfruit.kindweather.screens

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes
import com.microsoft.appcenter.distribute.Distribute
import com.tragicfruit.kindweather.BuildConfig
import com.tragicfruit.kindweather.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @ColorRes
    var statusBarColor = R.color.background
    var lightStatusBar = true

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        applyStatusBarColorRes(statusBarColor, lightStatusBar)

        AppCenter.start(application, BuildConfig.APP_CENTER,
            Analytics::class.java,
            Crashes::class.java,
            Distribute::class.java)
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