package com.tragicfruit.weatherapp.components

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.view.setPadding
import com.tragicfruit.weatherapp.R
import com.tragicfruit.weatherapp.utils.ViewHelper

class AllowLocationInlineView : RelativeLayout {

    private val allowButton = Button(context)

    @JvmOverloads
    constructor(context: Context, attributeSet: AttributeSet? = null, style: Int = 0): super(context, attributeSet, style) {
        setPadding(ViewHelper.px(R.dimen.app_margin).toInt())

        allowButton.setText(R.string.alerts_allow_location_button)
        addView(allowButton, LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
            addRule(RelativeLayout.ALIGN_PARENT_END)
            addRule(RelativeLayout.CENTER_VERTICAL)
        })

        val infoText = TextView(context)
        infoText.setText(R.string.alerts_allow_location_text)
        infoText.setTextAppearance(context, R.style.TextAppearance_AppCompat_Body1)
        addView(infoText, LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
            addRule(RelativeLayout.START_OF, ViewHelper.getViewId(allowButton))
            addRule(RelativeLayout.CENTER_VERTICAL)
            marginStart = ViewHelper.px(R.dimen.app_margin).toInt()
            marginEnd = ViewHelper.px(R.dimen.app_margin).toInt()
        })
    }

    fun setButtonClickListener(onClickListener: (View) -> Unit) {
        allowButton.setOnClickListener(onClickListener)
    }

}