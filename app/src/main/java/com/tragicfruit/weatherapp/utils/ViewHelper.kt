package com.tragicfruit.weatherapp.utils

import android.content.Context
import android.content.res.Resources
import android.view.View
import androidx.annotation.DimenRes

object ViewHelper {

    private var resources: Resources? = null

    fun init(context: Context) {
        this.resources = context.resources
    }

    fun px(@DimenRes dimen: Int): Float {
        return resources?.getDimension(dimen) ?: 0f
    }

    fun dp(@DimenRes dimen: Int): Int {
        resources?.let { res ->
            return (res.getDimension(dimen) / res.displayMetrics.density).toInt()
        } ?: return 0
    }

    fun getViewId(view: View): Int {
        if (view.id == View.NO_ID) {
            view.id = View.generateViewId()
        }

        return view.id
    }

}