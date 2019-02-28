package com.tragicfruit.weatherapp.screens.alert.fragments.list

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.tragicfruit.weatherapp.R
import com.tragicfruit.weatherapp.model.WeatherAlert
import com.tragicfruit.weatherapp.utils.ViewHelper

class AlertCell(context: Context) : RelativeLayout(context) {

    private val backgroundImage = ImageView(context)
    private val nameView = TextView(context)

    init {
        setPadding(0, ViewHelper.px(R.dimen.app_margin_h).toInt(), 0, ViewHelper.px(R.dimen.app_margin_h).toInt())

        addView(backgroundImage, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT))

        nameView.gravity = Gravity.CENTER_HORIZONTAL
        addView(nameView, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT).apply {
            addRule(RelativeLayout.CENTER_VERTICAL)
        })
    }

    fun setData(alert: WeatherAlert) {
        nameView.text = alert.name
        backgroundImage.setBackgroundColor(if (alert.enabled) alert.color else Color.GRAY)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec / 2)
    }

}