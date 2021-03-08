package com.tragicfruit.kindweather.ui.components

import android.content.Context
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import com.jaygoo.widget.OnRangeChangedListener
import com.jaygoo.widget.RangeSeekBar
import com.tragicfruit.kindweather.R
import com.tragicfruit.kindweather.data.WeatherAlertParam
import com.tragicfruit.kindweather.utils.DisplayUtils
import com.tragicfruit.kindweather.utils.ViewHelper
import com.tragicfruit.kindweather.utils.getViewId
import com.tragicfruit.kindweather.utils.setMargins
import com.tragicfruit.kindweather.utils.setPadding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AlertDetailParamView(context: Context) : RelativeLayout(context) {

    @Inject lateinit var viewHelper: ViewHelper

    private val paramTitle = TextView(context)
    private val slider = RangeSeekBar(context)
    private val lowerBoundText = TextView(context)
    private val upperBoundText = TextView(context)

    var lowerBound: Double? = null; private set
    var upperBound: Double? = null; private set

    init {
        setPadding(0, viewHelper.parsePx(R.dimen.app_margin_xx))

        paramTitle.setTextColor(ContextCompat.getColor(context, R.color.text_black))
        addView(
            paramTitle,
            LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT).apply {
                setMargins(viewHelper.parsePx(R.dimen.app_margin_xx), 0)
            }
        )

        slider.leftSeekBar.thumbSize = viewHelper.parsePx(R.dimen.seekbar_thumb_size)
        slider.rightSeekBar.thumbSize = viewHelper.parsePx(R.dimen.seekbar_thumb_size)
        slider.leftSeekBar.thumbDrawableId = R.drawable.seekbar_thumb
        slider.rightSeekBar.thumbDrawableId = R.drawable.seekbar_thumb
        addView(
            slider,
            LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT).apply {
                addRule(BELOW, paramTitle.getViewId())
                setMargins(0, viewHelper.parsePx(R.dimen.app_margin), 0, 0)
            }
        )

        lowerBoundText.setTextColor(ContextCompat.getColor(context, R.color.text_grey))
        addView(
            lowerBoundText,
            LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
                addRule(BELOW, slider.getViewId())
                addRule(ALIGN_PARENT_LEFT)
                marginStart = viewHelper.parsePx(R.dimen.app_margin_xxx)
            }
        )

        upperBoundText.setTextColor(ContextCompat.getColor(context, R.color.text_grey))
        addView(
            upperBoundText,
            LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
                addRule(BELOW, slider.getViewId())
                addRule(ALIGN_PARENT_RIGHT)
                marginEnd = viewHelper.parsePx(R.dimen.app_margin_xxx)
            }
        )
    }

    fun setData(
        @ColorInt color: Int,
        param: WeatherAlertParam,
        usesImperialUnits: Boolean,
        listener: Listener?
    ) {
        paramTitle.setText(param.dataType.label)
        slider.progressColor = color

        val minValue = param.dataType.getMinValue(usesImperialUnits).toFloat()
        val maxValue = param.dataType.getMaxValue(usesImperialUnits).toFloat()
        slider.setRange(minValue, maxValue)

        // Configure slider for bounds
        val leftValue = param.getLowerBound(usesImperialUnits)?.toFloat() ?: minValue
        val rightValue = param.getUpperBound(usesImperialUnits)?.toFloat() ?: maxValue
        slider.setValue(leftValue, rightValue)
        lowerBoundText.text = DisplayUtils.getMeasurementString(
            value = leftValue,
            units = param.dataType.getUnits(usesImperialUnits)
        )
        upperBoundText.text = DisplayUtils.getMeasurementString(
            value = rightValue,
            units = param.dataType.getUnits(usesImperialUnits)
        )

        slider.setOnRangeChangedListener(object : OnRangeChangedListener {
            override fun onStartTrackingTouch(view: RangeSeekBar?, isLeft: Boolean) = Unit

            override fun onRangeChanged(
                view: RangeSeekBar?,
                leftValue: Float,
                rightValue: Float,
                isFromUser: Boolean
            ) {
                lowerBound = if (leftValue > minValue) leftValue.toDouble() else null
                upperBound = if (rightValue < maxValue) rightValue.toDouble() else null

                lowerBoundText.text = DisplayUtils.getMeasurementString(
                    value = leftValue,
                    units = param.dataType.getUnits(usesImperialUnits)
                )
                upperBoundText.text = DisplayUtils.getMeasurementString(
                    value = rightValue,
                    units = param.dataType.getUnits(usesImperialUnits)
                )
            }

            override fun onStopTrackingTouch(view: RangeSeekBar?, isLeft: Boolean) {
                if (isLeft) {
                    listener?.onLowerBoundChanged(param, lowerBound)
                } else {
                    listener?.onUpperBoundChanged(param, upperBound)
                }
            }
        })
    }

    interface Listener {
        fun onLowerBoundChanged(param: WeatherAlertParam, value: Double?)
        fun onUpperBoundChanged(param: WeatherAlertParam, value: Double?)
    }
}
