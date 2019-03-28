package com.tragicfruit.kindweather.components

import android.content.Context
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.jaygoo.widget.OnRangeChangedListener
import com.jaygoo.widget.RangeSeekBar
import com.tragicfruit.kindweather.R
import com.tragicfruit.kindweather.model.WeatherAlertParam
import com.tragicfruit.kindweather.utils.*

class AlertDetailParamView(context: Context) : RelativeLayout(context) {

    private val paramTitle = TextView(context)
    private val slider = RangeSeekBar(context)
    private val lowerBoundText = TextView(context)
    private val upperBoundText = TextView(context)

    var lowerBound: Double? = null; private set
    var upperBound: Double? = null; private set

    init {
        setPadding(0, ViewHelper.parsePx(R.dimen.app_margin_xx))

        paramTitle.setTextColor(ContextCompat.getColor(context, R.color.text_black))
        addView(paramTitle, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT).apply {
            setMargins(ViewHelper.parsePx(R.dimen.app_margin_xx), 0)
        })

        slider.leftSeekBar.thumbSize = ViewHelper.parsePx(R.dimen.seekbar_thumb_size)
        slider.rightSeekBar.thumbSize = ViewHelper.parsePx(R.dimen.seekbar_thumb_size)
        slider.leftSeekBar.thumbDrawableId = R.drawable.seekbar_thumb
        slider.rightSeekBar.thumbDrawableId = R.drawable.seekbar_thumb
        addView(slider, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT).apply {
            addRule(BELOW, paramTitle.getViewId())
            setMargins(0, ViewHelper.parsePx(R.dimen.app_margin), 0, 0)
        })

        lowerBoundText.setTextColor(ContextCompat.getColor(context, R.color.text_grey))
        addView(lowerBoundText, LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
            addRule(BELOW, slider.getViewId())
            addRule(ALIGN_PARENT_LEFT)
            marginStart = ViewHelper.parsePx(R.dimen.app_margin_xxx)
        })

        upperBoundText.setTextColor(ContextCompat.getColor(context, R.color.text_grey))
        addView(upperBoundText, LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
            addRule(BELOW, slider.getViewId())
            addRule(ALIGN_PARENT_RIGHT)
            marginEnd = ViewHelper.parsePx(R.dimen.app_margin_xxx)
        })
    }

    fun setData(color: Int, param: WeatherAlertParam, listener: Listener?) {
        val type = param.getType()

        paramTitle.setText(type.label)
        slider.progressColor = color

        val minValue = type.minValue.toFloat()
        val maxValue = type.maxValue.toFloat()
        slider.setRange(minValue, maxValue)

        // Configure slider for bounds
        val leftValue = param.lowerBound?.toFloat() ?: minValue
        val rightValue = param.upperBound?.toFloat() ?: maxValue
        slider.setValue(leftValue, rightValue)
        lowerBoundText.text = DisplayUtils.getMeasurementString(leftValue, type.units)
        upperBoundText.text = DisplayUtils.getMeasurementString(rightValue, type.units)

        slider.setOnRangeChangedListener(object : OnRangeChangedListener {
            override fun onStartTrackingTouch(view: RangeSeekBar?, isLeft: Boolean) {

            }

            override fun onRangeChanged(view: RangeSeekBar?, leftValue: Float, rightValue: Float, isFromUser: Boolean) {
                lowerBound = if (leftValue > minValue) leftValue.toDouble() else null
                upperBound = if (rightValue < maxValue) rightValue.toDouble() else null

                lowerBoundText.text = DisplayUtils.getMeasurementString(leftValue, type.units)
                upperBoundText.text = DisplayUtils.getMeasurementString(rightValue, type.units)
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