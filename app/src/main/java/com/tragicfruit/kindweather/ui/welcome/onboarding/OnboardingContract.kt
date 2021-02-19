package com.tragicfruit.kindweather.ui.welcome.onboarding

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.tragicfruit.kindweather.ui.WPresenter
import com.tragicfruit.kindweather.ui.WView

interface OnboardingContract {

    interface View : WView {
        fun setImage(@DrawableRes imageRes: Int)
        fun setTitle(@StringRes titleRes: Int)
        fun setDescription(@StringRes descRes: Int)
    }

    interface Presenter : WPresenter<View> {
        fun init(imageRes: Int, titleRes: Int, descRes: Int)
    }

}