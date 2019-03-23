package com.tragicfruit.kindweather.components

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.view.setPadding
import com.tragicfruit.kindweather.R
import com.tragicfruit.kindweather.utils.ViewHelper
import com.tragicfruit.kindweather.utils.getViewId

class AllowLocationInlineView : RelativeLayout {

    private val allowButton = Button(context)

    @JvmOverloads
    constructor(context: Context, attributeSet: AttributeSet? = null, style: Int = 0): super(context, attributeSet, style) {
        setPadding(ViewHelper.parsePx(R.dimen.app_margin))

        allowButton.setText(R.string.alert_list_allow_location_button)
        addView(allowButton, LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
            addRule(ALIGN_PARENT_END)
            addRule(CENTER_VERTICAL)
        })

        val infoText = TextView(context)
        infoText.setText(R.string.alert_list_allow_location_text)
        infoText.setTextAppearance(context, R.style.TextAppearance_AppCompat_Body1)
        addView(infoText, LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
            addRule(START_OF, allowButton.getViewId())
            addRule(CENTER_VERTICAL)
            marginStart = ViewHelper.parsePx(R.dimen.app_margin)
            marginEnd = ViewHelper.parsePx(R.dimen.app_margin)
        })
    }

    fun setButtonClickListener(onClickListener: (View) -> Unit) {
        allowButton.setOnClickListener(onClickListener)
    }

}