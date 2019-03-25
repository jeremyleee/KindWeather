package com.tragicfruit.kindweather.components

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.bumptech.glide.Glide
import com.tragicfruit.kindweather.R
import com.tragicfruit.kindweather.model.WeatherAlert
import com.tragicfruit.kindweather.utils.ViewHelper
import com.tragicfruit.kindweather.utils.setMargins

class AlertCell(context: Context, private val listener: Listener? = null) : LinearLayout(context) {

    private val nameView = TextView(context)
    private val illustrationView = AlertCellImageView(context)

    init {
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT).apply {
            setMargins(ViewHelper.parsePx(R.dimen.app_margin_xx), ViewHelper.parsePx(R.dimen.app_margin))
        }

        orientation = HORIZONTAL
        setBackgroundResource(R.drawable.alert_cell_background)

        nameView.textSize = 28f
        nameView.setLineSpacing(ViewHelper.toPx(4).toFloat(), 1f)
        nameView.typeface = ResourcesCompat.getFont(context, R.font.playfair_bold)
        nameView.setTextColor(ContextCompat.getColor(context, R.color.alert_title))
        nameView.gravity = Gravity.CENTER_VERTICAL
        addView(nameView, LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT).apply {
            setMargins(ViewHelper.parsePx(R.dimen.app_margin_xxx), 0, 0, 0)
            weight = 1f
        })

        illustrationView.adjustViewBounds = true
        illustrationView.scaleType = ImageView.ScaleType.FIT_XY
        addView(illustrationView, LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT).apply {
            setMargins(0, ViewHelper.parsePx(R.dimen.app_margin), ViewHelper.parsePx(R.dimen.app_margin_xx), 0)
            weight = 1f
        })
    }

    fun setData(alert: WeatherAlert) {
        setOnClickListener {
            listener?.onAlertClicked(alert)
        }

        nameView.setText(alert.getInfo().title)

        val backgroundColor = if (alert.enabled) {
            ContextCompat.getColor(context, alert.getInfo().color)
        } else Color.LTGRAY
        background.setColorFilter(backgroundColor, PorterDuff.Mode.SRC_IN)

        Glide.with(this)
            .load(alert.getInfo().image)
            .into(illustrationView)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val height = MeasureSpec.getSize(widthMeasureSpec) * 0.5625
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(height.toInt(), MeasureSpec.EXACTLY))
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