package com.tragicfruit.kindweather.ui.components

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.setPadding
import com.tragicfruit.kindweather.R
import com.tragicfruit.kindweather.util.ViewHelper
import com.tragicfruit.kindweather.util.getViewId
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingsListItemView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    style: Int = 0
) : RelativeLayout(context, attributeSet, style) {

    @Inject lateinit var viewHelper: ViewHelper

    private val title = TextView(context)
    private val subtitle = TextView(context)

    init {
        setPadding(viewHelper.parsePx(R.dimen.app_margin_xx))

        title.typeface = ResourcesCompat.getFont(context, R.font.lato_bold)
        title.textSize = 16f
        title.setTextColor(ContextCompat.getColor(context, R.color.text_black))
        addView(title, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)

        subtitle.typeface = ResourcesCompat.getFont(context, R.font.lato_regular)
        subtitle.textSize = 14f
        subtitle.setTextColor(ContextCompat.getColor(context, R.color.text_grey))
        addView(
            subtitle,
            LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
                addRule(BELOW, title.getViewId())
                setMargins(0, viewHelper.parsePx(R.dimen.app_margin_h), 0, 0)
            }
        )

        attributeSet?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.SettingsListItemView)
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
