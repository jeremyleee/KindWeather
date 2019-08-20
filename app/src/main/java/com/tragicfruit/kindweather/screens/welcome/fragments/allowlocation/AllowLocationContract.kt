package com.tragicfruit.kindweather.screens.welcome.fragments.allowlocation

import com.tragicfruit.kindweather.screens.WPresenter
import com.tragicfruit.kindweather.screens.WView

interface AllowLocationContract {

    interface View : WView {
        fun requestLocationPermission()
        fun showNextScreen()
        fun showPermissionsRequiredError()
    }

    interface Presenter : WPresenter<View> {
        fun onAllowClicked()
        fun onPermissionAllowed()
        fun onPermissionDenied()
    }

    interface Callback {
        fun onLocationPermissionGranted()
    }

}