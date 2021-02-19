package com.tragicfruit.kindweather.ui.welcome.fragments.allowlocation

class AllowLocationPresenter(override var view: AllowLocationContract.View) : AllowLocationContract.Presenter {

    override fun onAllowClicked() {
        view.requestLocationPermission()
    }

    override fun onPermissionAllowed() {
        view.showNextScreen()
    }

    override fun onPermissionDenied() {
        view.showPermissionsRequiredError()
    }

}