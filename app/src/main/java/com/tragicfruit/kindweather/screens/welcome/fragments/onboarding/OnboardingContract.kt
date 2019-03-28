package com.tragicfruit.kindweather.screens.welcome.fragments.onboarding

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.tragicfruit.kindweather.screens.WPresenter
import com.tragicfruit.kindweather.screens.WView

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