package com.tragicfruit.kindweather.components

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.view.Gravity
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.constraintlayout.widget.Guideline
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.tragicfruit.kindweather.R
import com.tragicfruit.kindweather.model.WeatherAlert
import com.tragicfruit.kindweather.utils.RoundRectOutlineProvider
import com.tragicfruit.kindweather.utils.ViewHelper
import com.tragicfruit.kindweather.utils.getViewId
import com.tragicfruit.kindweather.utils.setMargins


class AlertCell(context: Context, private val listener: Listener? = null) : ConstraintLayout(context) {

    private val titleView = TextView(context)
    private val illustrationView = AlertCellImageView(context)

    init {
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT).apply {
            setMargins(ViewHelper.parsePx(R.dimen.app_margin_xx), ViewHelper.parsePx(R.dimen.app_margin))
        }

        setBackgroundResource(R.drawable.round_rect_cell_background)
        elevation = ViewHelper.parsePx(R.dimen.app_margin).toFloat()
        outlineProvider = RoundRectOutlineProvider(ViewHelper.parsePx(R.dimen.round_rect_cell_radius).toFloat())

        illustrationView.adjustViewBounds = true
        illustrationView.scaleType = ImageView.ScaleType.FIT_XY
        addView(illustrationView, LayoutParams(LayoutParams.MATCH_CONSTRAINT, LayoutParams.WRAP_CONTENT))

        titleView.textSize = 28f
        titleView.setLineSpacing(ViewHelper.toPx(-4).toFloat(), 1f)
        titleView.typeface = ResourcesCompat.getFont(context, R.font.playfair_bold)
        titleView.setTextColor(ContextCompat.getColor(context, R.color.alert_title))
        titleView.gravity = Gravity.CENTER_VERTICAL
        addView(titleView, LayoutParams(LayoutParams.MATCH_CONSTRAINT, LayoutParams.MATCH_CONSTRAINT))

        val titleGuidelineId = Guideline(context).getViewId()
        val titleSet = ConstraintSet().also {
            it.create(titleGuidelineId, ConstraintSet.VERTICAL_GUIDELINE)
            it.setGuidelinePercent(titleGuidelineId, 0.5f)
            it.connect(titleView.getViewId(), ConstraintSet.START, this.getViewId(), ConstraintSet.START, ViewHelper.parsePx(R.dimen.app_margin_xxx))
            it.connect(titleView.getViewId(), ConstraintSet.END, titleGuidelineId, ConstraintSet.START)
            it.connect(titleView.getViewId(), ConstraintSet.TOP, this.getViewId(), ConstraintSet.TOP)
            it.connect(titleView.getViewId(), ConstraintSet.BOTTOM, this.getViewId(), ConstraintSet.BOTTOM)
        }

        val imageGuidelineId = Guideline(context).getViewId()
        val illustrationSet = ConstraintSet().also {
            it.create(imageGuidelineId, ConstraintSet.VERTICAL_GUIDELINE)
            it.setGuidelinePercent(imageGuidelineId, 0.4f)
            it.connect(illustrationView.getViewId(), ConstraintSet.TOP, this.getViewId(), ConstraintSet.TOP, ViewHelper.parsePx(R.dimen.app_margin))
            it.connect(illustrationView.getViewId(), ConstraintSet.START, imageGuidelineId, ConstraintSet.END)
            it.connect(illustrationView.getViewId(), ConstraintSet.END, this.getViewId(), ConstraintSet.END, ViewHelper.parsePx(R.dimen.app_margin_xx))
        }

        titleSet.applyTo(this)
        illustrationSet.applyTo(this)
    }

    fun setData(alert: WeatherAlert) {
        setOnClickListener {
            listener?.onAlertClicked(alert)
        }

        titleView.setText(alert.getInfo().title)

        val backgroundColor = if (alert.enabled) {
            ContextCompat.getColor(context, alert.getInfo().color)
        } else Color.LTGRAY
        background.setColorFilter(backgroundColor, PorterDuff.Mode.SRC_IN)

        if (alert.getInfo().image != 0) {
            illustrationView.setImageResource(alert.getInfo().image)
        }
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
        super.onMeasure(widthMeasureSpec,
            MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(heightMeasureSpec), MeasureSpec.UNSPECIFIED))
    }

}