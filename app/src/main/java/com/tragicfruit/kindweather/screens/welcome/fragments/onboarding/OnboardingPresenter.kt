package com.tragicfruit.kindweather.screens.welcome.fragments.onboarding

class OnboardingPresenter(override var view: OnboardingContract.View) : OnboardingContract.Presenter {

    override fun init(titleRes: Int, descRes: Int) {
        if (titleRes != 0) {
            view.setTitle(titleRes)
        }

        if (descRes != 0) {
            view.setDescription(descRes)
        }
    }

}