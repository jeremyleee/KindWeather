package com.tragicfruit.kindweather.ui.components

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.tragicfruit.kindweather.R
import com.tragicfruit.kindweather.utils.ViewHelper
import com.tragicfruit.kindweather.utils.setMargins
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OnboardingIndicator @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    style: Int = 0
) : LinearLayout(context, attributeSet, style) {

    @Inject lateinit var viewHelper: ViewHelper

    init {
        orientation = HORIZONTAL
        val pageCount = attributeSet?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.OnboardingIndicator)

            try {
                typedArray.getInt(R.styleable.OnboardingIndicator_pageCount, 0)
            } finally {
                typedArray.recycle()
            }
        } ?: 0
        setPageCount(pageCount)
    }

    fun setPageCount(pageCount: Int, initialPage: Int = 0) {
        removeAllViews()
        for (i in 0 until pageCount) {
            val indicator = OnboardingPageIndicator(context)
            indicator.setCurrent(i == initialPage, false)
            addView(indicator, LayoutParams(
                viewHelper.parsePx(R.dimen.onboarding_indicator_size),
                viewHelper.parsePx(R.dimen.onboarding_indicator_size)).apply {
                setMargins(viewHelper.parsePx(R.dimen.app_margin), 0)
            })
        }
    }

    fun setCurrentPage(pageIndex: Int) {
        for (i in 0 until childCount) {
            val indicator = getChildAt(i) as? OnboardingPageIndicator
            indicator?.setCurrent(i == pageIndex, true)
        }
    }

}

class OnboardingPageIndicator(context: Context) : View(context) {

    init {
        setBackgroundResource(R.drawable.onboarding_indicator_unselected)
    }

    fun setCurrent(current: Boolean, animated: Boolean) {
        // TODO: animation

        val drawable = if (current) R.drawable.onboarding_indicator_selected else R.drawable.onboarding_indicator_unselected
        setBackgroundResource(drawable)
    }

}