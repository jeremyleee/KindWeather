package com.tragicfruit.kindweather.screens.welcome.fragments.allowlocation

import com.tragicfruit.kindweather.screens.WPresenter
import com.tragicfruit.kindweather.screens.WView

interface AllowLocationContract {

    interface View : WView {
        fun requestLocationPermission()
        fun showNextScreen()
    }

    interface Presenter : WPresenter<View> {
        fun onAllowClicked()
        fun onPermissionAllowed()
    }

    interface Callback {
        fun onLocationPermissionGranted()
    }

}