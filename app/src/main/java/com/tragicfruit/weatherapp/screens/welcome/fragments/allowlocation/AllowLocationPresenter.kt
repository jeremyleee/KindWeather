package com.tragicfruit.weatherapp.screens.welcome.fragments.allowlocation

class AllowLocationPresenter(override var view: AllowLocationContract.View) : AllowLocationContract.Presenter {

    override fun onAllowClicked() {
        view.requestLocationPermission()
    }

    override fun onPermissionAllowed() {
        view.showNextScreen()
    }

}