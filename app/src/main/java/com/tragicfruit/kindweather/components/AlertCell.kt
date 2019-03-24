package com.tragicfruit.kindweather.components

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.bumptech.glide.Glide
import com.tragicfruit.kindweather.R
import com.tragicfruit.kindweather.model.WeatherAlert
import com.tragicfruit.kindweather.utils.ViewHelper
import com.tragicfruit.kindweather.utils.setMargins

class AlertCell(context: Context, private val listener: Listener? = null) : RelativeLayout(context) {

    private val cellContainer = RelativeLayout(context)
    private val backgroundView = ImageView(context)
    private val nameView = TextView(context)
    private val illustrationView = AlertCellImageView(context)

    init {
        addView(cellContainer, LayoutParams(LayoutParams.MATCH_PARENT, ViewHelper.parsePx(R.dimen.alert_cell_height)).apply {
            setMargins(ViewHelper.parsePx(R.dimen.app_margin_xx), ViewHelper.parsePx(R.dimen.app_margin))
        })

        backgroundView.setImageResource(R.drawable.alert_cell_background)
        cellContainer.addView(backgroundView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)

        illustrationView.adjustViewBounds = true
        illustrationView.scaleType = ImageView.ScaleType.FIT_XY
        cellContainer.addView(illustrationView, LayoutParams(ViewHelper.toPx(220), LayoutParams.WRAP_CONTENT).apply {
            addRule(ALIGN_PARENT_END)
            setMargins(0, ViewHelper.parsePx(R.dimen.app_margin), ViewHelper.parsePx(R.dimen.app_margin_xx), 0)
        })

        nameView.textSize = 36f
        nameView.typeface = ResourcesCompat.getFont(context, R.font.playfair_bold)
        nameView.setTextColor(ContextCompat.getColor(context, R.color.alert_title))
        cellContainer.addView(nameView, LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
            setMargins(ViewHelper.parsePx(R.dimen.app_margin_xxx), ViewHelper.toPx(42), 0, 0)
        })
    }

    fun setData(alert: WeatherAlert) {
        setOnClickListener {
            listener?.onAlertClicked(alert)
        }

        nameView.setText(alert.getInfo().title)

        val backgroundColor = if (alert.enabled) {
            ContextCompat.getColor(context, alert.getInfo().color)
        } else Color.GRAY
        backgroundView.setColorFilter(backgroundColor, PorterDuff.Mode.SRC_IN)

        Glide.with(this)
            .load(alert.getInfo().image)
            .into(illustrationView)
    }

    interface Listener {
        fun onAlertClicked(alert: WeatherAlert)
    }

}

class AlertCellImageView(context: Context) : ImageView(context) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // Allow height to be larger than parent view
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(heightMeasureSpec, MeasureSpec.UNSPECIFIED))
    }

}