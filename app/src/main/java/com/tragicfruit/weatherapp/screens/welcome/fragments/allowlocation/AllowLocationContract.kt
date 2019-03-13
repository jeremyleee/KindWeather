package com.tragicfruit.weatherapp.screens.welcome.fragments.allowlocation

import com.tragicfruit.weatherapp.screens.WPresenter
import com.tragicfruit.weatherapp.screens.WView

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