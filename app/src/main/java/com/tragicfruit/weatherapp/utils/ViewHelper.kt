package com.tragicfruit.weatherapp.utils

import android.content.Context
import android.content.res.Resources
import androidx.annotation.DimenRes

object ViewHelper {

    private var resources: Resources? = null

    fun init(context: Context) {
        this.resources = context.resources
    }

    fun toPx(dp: Int): Int {
        resources?.let { res ->
            return (dp * res.displayMetrics.density).toInt()
        } ?: return 0
    }

    fun parsePx(@DimenRes dimen: Int): Int {
        return resources?.getDimension(dimen)?.toInt() ?: 0
    }

}