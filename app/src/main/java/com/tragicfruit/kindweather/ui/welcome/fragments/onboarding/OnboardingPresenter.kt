package com.tragicfruit.kindweather.ui.welcome.fragments.onboarding

class OnboardingPresenter(override var view: OnboardingContract.View) : OnboardingContract.Presenter {

    override fun init(imageRes: Int, titleRes: Int, descRes: Int) {
        if (imageRes != 0) {
            view.setImage(imageRes)
        }

        if (titleRes != 0) {
            view.setTitle(titleRes)
        }

        if (descRes != 0) {
            view.setDescription(descRes)
        }
    }

}