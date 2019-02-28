package com.tragicfruit.weatherapp.utils

import android.content.res.Resources
import androidx.annotation.DimenRes

object ViewHelper {

    private var resources: Resources? = null

    fun init(resources: Resources) {
        this.resources = resources
    }

    fun px(@DimenRes dimen: Int): Float {
        return resources?.getDimension(dimen) ?: 0f
    }

    fun dp(@DimenRes dimen: Int): Int {
        resources?.let { res ->
            return (res.getDimension(dimen) / res.displayMetrics.density).toInt()
        } ?: return 0
    }

}