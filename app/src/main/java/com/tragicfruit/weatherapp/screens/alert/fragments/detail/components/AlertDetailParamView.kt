package com.tragicfruit.weatherapp.screens.alert.fragments.detail.components

import android.content.Context
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.core.view.setPadding
import com.tragicfruit.weatherapp.R
import com.tragicfruit.weatherapp.model.WeatherAlertParam
import com.tragicfruit.weatherapp.utils.ViewHelper
import com.tragicfruit.weatherapp.utils.setFormattedText

class AlertDetailParamView(context: Context) : RelativeLayout(context) {

    private val paramTitle = TextView(context)
    private val paramLessDesc = TextView(context)
    private val paramGreaterDesc = TextView(context)

    init {
        setPadding(ViewHelper.px(R.dimen.app_margin_xx).toInt())

        val detailsContainer = LinearLayout(context)
        detailsContainer.orientation = LinearLayout.VERTICAL
        addView(detailsContainer, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT))

        paramTitle.setTextAppearance(context, R.style.TextAppearance_AppCompat_Body2)
        paramLessDesc.setTextAppearance(context, R.style.TextAppearance_AppCompat_Body1)
        paramGreaterDesc.setTextAppearance(context, R.style.TextAppearance_AppCompat_Body1)

        detailsContainer.addView(paramTitle, LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT))
        detailsContainer.addView(paramLessDesc, LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT))
        detailsContainer.addView(paramGreaterDesc, LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT))
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
        }, param.lessThan, param.getType()?.units)

        paramGreaterDesc.isVisible = param.greaterThan != null
        paramGreaterDesc.setFormattedText(if (param.greaterThanInclusive) {
            R.string.alert_detail_gt_equal
        } else {
            R.string.alert_detail_gt
        }, param.greaterThan, param.getType()?.units)


    }

}