package com.tragicfruit.weatherapp.components

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.core.view.setPadding
import com.tragicfruit.weatherapp.R
import com.tragicfruit.weatherapp.utils.ViewHelper
import com.tragicfruit.weatherapp.utils.getViewId

class SettingsListItemView : RelativeLayout {

    private val title = TextView(context)
    private val subtitle = TextView(context)

    @JvmOverloads
    constructor(context: Context, attributeSet: AttributeSet? = null, style: Int = 0): super(context, attributeSet, style) {
        setPadding(ViewHelper.parsePx(R.dimen.app_margin_xx))

        title.setTextAppearance(context, R.style.TextAppearance_AppCompat_Body2)
        addView(title, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)

        subtitle.setTextAppearance(context, R.style.TextAppearance_AppCompat_Body1)
        addView(subtitle, LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
            addRule(BELOW, title.getViewId())
        })

        attributeSet?.let {
            val typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.SettingsListItemView)
            title.text = typedArray.getString(R.styleable.SettingsListItemView_title)
            typedArray.recycle()
        }
    }

    fun setTitle(@StringRes resId: Int) {
        title.setText(resId)
    }

    fun setSubtitle(@StringRes resId: Int) {
        subtitle.setText(resId)
    }

    fun setSubtitle(subtitleText: String) {
        subtitle.text = subtitleText
    }

}