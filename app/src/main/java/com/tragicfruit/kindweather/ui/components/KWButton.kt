package com.tragicfruit.kindweather.ui.components

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.tragicfruit.kindweather.R
import com.tragicfruit.kindweather.utils.ViewHelper
import com.tragicfruit.kindweather.utils.setPadding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class KWButton @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    style: Int = 0
) : TextView(context, attributeSet, style) {

    @Inject lateinit var viewHelper: ViewHelper

    init {
        setPadding(viewHelper.toPx(28), viewHelper.toPx(14))

        typeface = ResourcesCompat.getFont(context, R.font.lato_bold)
        textSize = 12f
        letterSpacing = 0.1f
        isAllCaps = true

        setTextColor(ContextCompat.getColor(context, R.color.text_black))
        setBackgroundResource(R.drawable.button_background)
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        val colorRes = if (enabled) R.color.text_black else R.color.text_lt_grey
        setTextColor(ContextCompat.getColor(context, colorRes))
    }

}