package com.tragicfruit.kindweather.util

import android.graphics.Color
import androidx.annotation.ColorInt
import kotlin.math.roundToInt

object ColorHelper {

    @ColorInt
    fun darkenColor(@ColorInt color: Int, factor: Float = 0.9f): Int {
        val a = Color.alpha(color)
        val r = (Color.red(color) * factor).roundToInt()
        val g = (Color.green(color) * factor).roundToInt()
        val b = (Color.blue(color) * factor).roundToInt()
        return Color.argb(
            a,
            r.coerceAtMost(255),
            g.coerceAtMost(255),
            b.coerceAtMost(255)
        )
    }
}
