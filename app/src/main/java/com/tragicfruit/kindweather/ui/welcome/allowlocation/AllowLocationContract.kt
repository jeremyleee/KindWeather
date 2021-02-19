package com.tragicfruit.kindweather.ui.welcome.allowlocation

import com.tragicfruit.kindweather.ui.WPresenter
import com.tragicfruit.kindweather.ui.WView

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