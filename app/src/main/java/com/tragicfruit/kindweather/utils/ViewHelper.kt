package com.tragicfruit.kindweather.utils

import android.content.Context
import android.content.res.Resources
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