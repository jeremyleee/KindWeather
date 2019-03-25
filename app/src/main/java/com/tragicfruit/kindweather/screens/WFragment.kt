package com.tragicfruit.kindweather.screens

import android.os.Bundle
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.tragicfruit.kindweather.R

open class WFragment : Fragment() {

    val baseActivity: WActivity?
        get() = activity as? WActivity

    @ColorRes
    open var statusBarColor = R.color.white
    open var lightStatusBar = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        applyStatusBarColorRes(statusBarColor, lightStatusBar)
    }

    protected fun applyStatusBarColor(@ColorInt color: Int, lightStatusBar: Boolean) {
        baseActivity?.applyStatusBarColor(color, lightStatusBar)
    }

    protected fun applyStatusBarColorRes(@ColorRes colorRes: Int, lightStatusBar: Boolean) {
        context?.let {
            applyStatusBarColor(ContextCompat.getColor(it, colorRes), lightStatusBar)
        }
    }

}