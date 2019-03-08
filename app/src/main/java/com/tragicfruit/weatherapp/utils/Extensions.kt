package com.tragicfruit.weatherapp.utils

import android.widget.TextView
import androidx.annotation.StringRes

fun TextView.setFormattedText(@StringRes resId: Int, vararg formatArgs: Any?) {
    text = context.getString(resId, *formatArgs)
}