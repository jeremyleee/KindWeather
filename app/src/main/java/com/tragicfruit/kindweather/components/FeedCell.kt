package com.tragicfruit.kindweather.components

import android.content.Context
import android.util.TypedValue
import android.view.Gravity
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.view.setPadding
import com.tragicfruit.kindweather.R
import com.tragicfruit.kindweather.controllers.ForecastIcon
import com.tragicfruit.kindweather.model.ForecastType
import com.tragicfruit.kindweather.model.WeatherNotification
import com.tragicfruit.kindweather.utils.DisplayUtils
import com.tragicfruit.kindweather.utils.ViewHelper
import com.tragicfruit.kindweather.utils.getViewId

class FeedCell(context: Context, private val listener: Listener? = null) : RelativeLayout(context) {

    private val dateView = TextView(context)
    private val icon = ImageView(context)
    private val highTempView = TextView(context)
    private val descriptionView = TextView(context)

    init {
        setPadding(ViewHelper.parsePx(R.dimen.app_margin_xx))

        val outValue = TypedValue()
        context.theme.resolveAttribute(android.R.attr.selectableItemBackground, outValue, true)
        setBackgroundResource(outValue.resourceId)

        dateView.setTextAppearance(context, R.style.TextAppearance_AppCompat_Body1)
        addView(dateView, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT).apply {
            setMargins(0, 0, 0, ViewHelper.parsePx(R.dimen.app_margin_xx))
        })

        icon.scaleType = ImageView.ScaleType.FIT_CENTER
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
        setOnClickListener {
            listener?.onFeedItemClicked(notification)
        }

        dateView.text = DisplayUtils.getSummaryDateString(notification.createdAt)
        icon.setImageResource(ForecastIcon.fromString(notification.forecast?.icon).iconRes)
        descriptionView.text = notification.description
        highTempView.text = notification.forecast?.getDataForType(ForecastType.TEMP_HIGH)?.getDisplayString()
    }

    interface Listener {
        fun onFeedItemClicked(notification: WeatherNotification)
    }

}