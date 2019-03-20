package com.tragicfruit.weatherapp.components

import android.content.Context
import android.view.Gravity
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.view.setPadding
import com.tragicfruit.weatherapp.R
import com.tragicfruit.weatherapp.model.ForecastType
import com.tragicfruit.weatherapp.model.WeatherNotification
import com.tragicfruit.weatherapp.utils.ColorHelper
import com.tragicfruit.weatherapp.utils.DisplayUtils
import com.tragicfruit.weatherapp.utils.ViewHelper
import com.tragicfruit.weatherapp.utils.getViewId

class FeedCell(context: Context) : RelativeLayout(context) {

    private val dateView = TextView(context)
    private val icon = ImageView(context)
    private val highTempView = TextView(context)
    private val descriptionView = TextView(context)

    init {
        setPadding(ViewHelper.parsePx(R.dimen.app_margin_xx))

        dateView.setTextAppearance(context, R.style.TextAppearance_AppCompat_Body1)
        addView(dateView, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT).apply {
            setMargins(0, 0, 0, ViewHelper.parsePx(R.dimen.app_margin_xx))
        })

        addView(icon, LayoutParams(ViewHelper.parsePx(R.dimen.weather_icon_size), ViewHelper.parsePx(R.dimen.weather_icon_size)).apply {
            addRule(BELOW, dateView.getViewId())
        })

        highTempView.setTextAppearance(context, R.style.TextAppearance_AppCompat_Caption)
        highTempView.gravity = Gravity.CENTER_HORIZONTAL
        addView(highTempView, LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
            addRule(ALIGN_START, icon.getViewId())
            addRule(ALIGN_END, icon.getViewId())
            addRule(BELOW, icon.getViewId())
            setMargins(0, ViewHelper.parsePx(R.dimen.app_margin), 0, 0)
        })

        descriptionView.setTextAppearance(context, R.style.TextAppearance_AppCompat_Body2)
        addView(descriptionView, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT).apply {
            addRule(BELOW, dateView.getViewId())
            addRule(END_OF, icon.getViewId())
            setMargins(ViewHelper.parsePx(R.dimen.app_margin_xx), 0, 0, 0)
        })
    }

    fun setData(notification: WeatherNotification) {
        dateView.text = DisplayUtils.getDateString(notification.createdAt)

        // TODO: set icon image
        icon.setBackgroundColor(ColorHelper.getRandomColor())

        descriptionView.text = notification.description
        highTempView.text = DisplayUtils.getMeasurementString(
            notification.highTemp.toFloat(),
            ForecastType.getUnits(ForecastType.Temp_high),
            0)
    }

}