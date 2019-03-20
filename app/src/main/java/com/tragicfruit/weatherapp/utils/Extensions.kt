package com.tragicfruit.weatherapp.utils

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.StringRes

fun TextView.setFormattedText(@StringRes resId: Int, vararg formatArgs: Any?) {
    text = context.getString(resId, *formatArgs)
}

fun Float.format(digits: Int): String {
    return String.format("%.${digits}f", this)
}

fun View.setPadding(x: Int, y: Int) {
    setPadding(x, y, x, y)
}

fun View.getViewId(): Int {
    if (this.id == View.NO_ID) {
        this.id = View.generateViewId()
    }

    return this.id
}

fun ViewGroup.MarginLayoutParams.setMargins(x: Int, y: Int) {
    setMargins(x, y, x, y)
}