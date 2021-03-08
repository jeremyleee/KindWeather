package com.tragicfruit.kindweather.ui.components

import android.content.Context
import android.graphics.PorterDuff
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.setMargins
import androidx.core.view.setPadding
import com.tragicfruit.kindweather.R
import com.tragicfruit.kindweather.data.WeatherNotification
import com.tragicfruit.kindweather.utils.DisplayUtils
import com.tragicfruit.kindweather.utils.RoundRectOutlineProvider
import com.tragicfruit.kindweather.utils.SharedPrefsHelper
import com.tragicfruit.kindweather.utils.ViewHelper
import com.tragicfruit.kindweather.utils.getViewId
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FeedCell(context: Context) : LinearLayout(context) {

    @Inject lateinit var sharedPrefsHelper: SharedPrefsHelper
    @Inject lateinit var viewHelper: ViewHelper

    private val dateView = TextView(context)
    private val cellContainer = RelativeLayout(context)
    private val icon = ImageView(context)
    private val highTempView = TextView(context)
    private val descriptionView = TextView(context)

    init {
        orientation = VERTICAL

        dateView.setTextColor(ContextCompat.getColor(context, R.color.text_lt_grey))
        addView(
            dateView,
            LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT).apply {
                setMargins(
                    viewHelper.parsePx(R.dimen.app_margin_xx),
                    viewHelper.parsePx(R.dimen.app_margin_xx),
                    viewHelper.parsePx(R.dimen.app_margin_xx),
                    0
                )
            }
        )

        cellContainer.setBackgroundResource(R.drawable.round_rect_cell_background)
        cellContainer.elevation = viewHelper.parsePx(R.dimen.app_margin).toFloat()
        cellContainer.outlineProvider =
            RoundRectOutlineProvider(viewHelper.parsePx(R.dimen.round_rect_cell_radius).toFloat())
        cellContainer.setPadding(viewHelper.parsePx(R.dimen.app_margin_xx))
        addView(
            cellContainer,
            LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT).apply {
                setMargins(viewHelper.parsePx(R.dimen.app_margin_xx))
            }
        )

        icon.scaleType = ImageView.ScaleType.FIT_CENTER
        cellContainer.addView(
            icon,
            viewHelper.parsePx(R.dimen.weather_icon_size),
            viewHelper.parsePx(R.dimen.weather_icon_size)
        )

        highTempView.setTextColor(ContextCompat.getColor(context, R.color.text_grey))
        highTempView.gravity = Gravity.CENTER_HORIZONTAL
        cellContainer.addView(
            highTempView,
            RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                addRule(RelativeLayout.ALIGN_START, icon.getViewId())
                addRule(RelativeLayout.ALIGN_END, icon.getViewId())
                addRule(RelativeLayout.BELOW, icon.getViewId())
                setMargins(0, viewHelper.parsePx(R.dimen.app_margin), 0, 0)
            }
        )

        descriptionView.textSize = 20f
        descriptionView.typeface = ResourcesCompat.getFont(context, R.font.lato_bold)
        descriptionView.setTextColor(ContextCompat.getColor(context, R.color.text_black))
        cellContainer.addView(
            descriptionView,
            RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                addRule(RelativeLayout.END_OF, icon.getViewId())
                addRule(RelativeLayout.CENTER_VERTICAL)
                setMargins(viewHelper.parsePx(R.dimen.app_margin_xxx), 0, 0, 0)
            }
        )
    }

    fun setData(notification: WeatherNotification, listener: Listener? = null) {
        setOnClickListener {
            listener?.onFeedItemClicked(notification)
        }

        dateView.text = DisplayUtils.getSummaryDateString(notification.createdAt)

        cellContainer.background.setColorFilter(notification.color, PorterDuff.Mode.SRC_IN)
        icon.setImageResource(notification.forecastIcon.iconRes)
        highTempView.text = notification.getTempHighString(sharedPrefsHelper.usesImperialUnits())
        descriptionView.text = notification.description
    }

    interface Listener {
        fun onFeedItemClicked(notification: WeatherNotification)
    }
}
