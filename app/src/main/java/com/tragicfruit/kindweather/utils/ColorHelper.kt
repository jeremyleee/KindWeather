package com.tragicfruit.kindweather.utils

import android.graphics.Color
import androidx.annotation.ColorInt

object ColorHelper {

    @ColorInt
    fun darkenColor(@ColorInt color: Int, factor: Float = 0.8f): Int {
        val a = Color.alpha(color)
        val r = Math.round(Color.red(color) * factor)
        val g = Math.round(Color.green(color) * factor)
        val b = Math.round(Color.blue(color) * factor)
        return Color.argb(
            a,
            Math.min(r, 255),
            Math.min(g, 255),
            Math.min(b, 255)
        )
    }

}