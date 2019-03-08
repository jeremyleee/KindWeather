package com.tragicfruit.weatherapp.screens.alert.fragments.detail.components

import android.content.Context
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.view.isVisible
import com.tragicfruit.weatherapp.R
import com.tragicfruit.weatherapp.model.WeatherAlertParam
import com.tragicfruit.weatherapp.utils.setFormattedText

class AlertDetailParamView(context: Context) : RelativeLayout(context) {

    private val paramTitle = TextView(context)
    private val paramLessDesc = TextView(context)
    private val paramGreaterDesc = TextView(context)

    init {
        val detailsContainer = LinearLayout(context)
        detailsContainer.orientation = LinearLayout.VERTICAL

        detailsContainer.addView(paramTitle)
        detailsContainer.addView(paramLessDesc)
        detailsContainer.addView(paramGreaterDesc)
    }

    fun setData(param: WeatherAlertParam) {
        param.getType()?.let { type ->
            paramTitle.setText(type.label)
        }

        paramLessDesc.isVisible = param.lessThan != null
        paramLessDesc.setFormattedText(if (param.lessThanInclusive) {
            R.string.alert_detail_lt_equal
        } else {
            R.string.alert_detail_lt
        },param.lessThan ?: 0)

        paramGreaterDesc.isVisible = param.greaterThan != null
        paramGreaterDesc.setFormattedText(if (param.greaterThanInclusive) {
            R.string.alert_detail_gt_equal
        } else {
            R.string.alert_detail_gt
        }, param.greaterThan ?: 0)


    }

}