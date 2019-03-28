package com.tragicfruit.kindweather.components

import android.content.Context
import android.graphics.PorterDuff
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.setPadding
import com.tragicfruit.kindweather.R
import com.tragicfruit.kindweather.controllers.ForecastIcon
import com.tragicfruit.kindweather.model.ForecastType
import com.tragicfruit.kindweather.model.WeatherNotification
import com.tragicfruit.kindweather.utils.DisplayUtils
import com.tragicfruit.kindweather.utils.ViewHelper
import com.tragicfruit.kindweather.utils.getViewId

class FeedCell(context: Context, private val listener: Listener? = null) : LinearLayout(context) {

    private val dateView = TextView(context)
    private val cellContainer = RelativeLayout(context)
    private val icon = ImageView(context)
    private val highTempView = TextView(context)
    private val descriptionView = TextView(context)

    init {
        setPadding(ViewHelper.parsePx(R.dimen.app_margin_xx))
        orientation = VERTICAL

        dateView.setTextColor(ContextCompat.getColor(context, R.color.text_lt_grey))
        addView(dateView, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT).apply {
            setMargins(0, 0, 0, ViewHelper.parsePx(R.dimen.app_margin_xx))
        })

        cellContainer.setBackgroundResource(R.drawable.alert_cell_background)
        cellContainer.setPadding(ViewHelper.parsePx(R.dimen.app_margin_xx))
        addView(cellContainer, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)

        icon.scaleType = ImageView.ScaleType.FIT_CENTER
        cellContainer.addView(icon, ViewHelper.parsePx(R.dimen.weather_icon_size), ViewHelper.parsePx(R.dimen.weather_icon_size))

        highTempView.setTextColor(ContextCompat.getColor(context, R.color.text_grey))
        highTempView.gravity = Gravity.CENTER_HORIZONTAL
        cellContainer.addView(highTempView, RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT).apply {
            addRule(RelativeLayout.ALIGN_START, icon.getViewId())
            addRule(RelativeLayout.ALIGN_END, icon.getViewId())
            addRule(RelativeLayout.BELOW, icon.getViewId())
            setMargins(0, ViewHelper.parsePx(R.dimen.app_margin), 0, 0)
        })

        descriptionView.textSize = 20f
        descriptionView.typeface = ResourcesCompat.getFont(context, R.font.lato_bold)
        descriptionView.setTextColor(ContextCompat.getColor(context, R.color.text_black))
        cellContainer.addView(descriptionView, RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT).apply {
            addRule(RelativeLayout.END_OF, icon.getViewId())
            addRule(RelativeLayout.CENTER_VERTICAL)
            setMargins(ViewHelper.parsePx(R.dimen.app_margin_xxx), 0, 0, 0)
        })
    }

    fun setData(notification: WeatherNotification) {
        setOnClickListener {
            listener?.onFeedItemClicked(notification)
        }

        dateView.text = DisplayUtils.getSummaryDateString(notification.createdAt)

        cellContainer.background.setColorFilter(notification.color, PorterDuff.Mode.SRC_IN)
        icon.setImageResource(ForecastIcon.fromString(notification.forecast?.icon).iconRes)
        highTempView.text = notification.forecast?.getDataForType(ForecastType.TEMP_HIGH)?.getDisplayString()
        descriptionView.text = notification.description
    }

    interface Listener {
        fun onFeedItemClicked(notification: WeatherNotification)
    }

}