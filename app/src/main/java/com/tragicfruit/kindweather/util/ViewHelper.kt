package com.tragicfruit.kindweather.util

import android.content.Context
import androidx.annotation.DimenRes

class ViewHelper(context: Context) {

    private val resources = context.resources

    fun toPx(dp: Int): Int {
        resources?.let { res ->
            return (dp * res.displayMetrics.density).toInt()
        } ?: return 0
    }

    fun parsePx(@DimenRes dimen: Int): Int {
        return resources?.getDimension(dimen)?.toInt() ?: 0
    }
}
