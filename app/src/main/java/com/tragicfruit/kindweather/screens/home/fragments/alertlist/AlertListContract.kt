package com.tragicfruit.kindweather.screens.home.fragments.alertlist

import com.tragicfruit.kindweather.model.WeatherAlert
import com.tragicfruit.kindweather.screens.WPresenter
import com.tragicfruit.kindweather.screens.WView
import io.realm.RealmResults

interface AlertListContract {

    interface View : WView {
        fun initView(alertList: RealmResults<WeatherAlert>)
        fun showAlertDetailScreen(alert: WeatherAlert, position: Int)
        fun refreshList()
        fun requestLocationPermission()
    }

    interface Presenter : WPresenter<View> {
        fun init()
        fun onAlertClicked(alert: WeatherAlert, position: Int)
        fun onAllowLocationClicked()
    }

}