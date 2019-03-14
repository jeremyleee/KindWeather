package com.tragicfruit.weatherapp.utils

import android.widget.TextView
import androidx.annotation.StringRes

fun TextView.setFormattedText(@StringRes resId: Int, vararg formatArgs: Any?) {
    text = context.getString(resId, *formatArgs)
}

fun Float.format(digits: Int): String {
    return String.format("%.${digits}f", this)
}