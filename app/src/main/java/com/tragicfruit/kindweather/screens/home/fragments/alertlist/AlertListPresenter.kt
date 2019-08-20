package com.tragicfruit.kindweather.screens.home.fragments.alertlist

import com.tragicfruit.kindweather.model.WeatherAlert
import io.realm.Realm
import io.realm.RealmResults
import io.realm.Sort
import io.realm.kotlin.where

class AlertListPresenter(override var view: AlertListContract.View) : AlertListContract.Presenter {

    private lateinit var alertList: RealmResults<WeatherAlert>

    override fun init() {
        alertList = Realm.getDefaultInstance()
            .where<WeatherAlert>()
            .sort("enabled", Sort.DESCENDING, "priority", Sort.ASCENDING)
            .findAll()

        view.initView(alertList)
    }

    override fun onAlertClicked(alert: WeatherAlert, position: Int) {
        view.showAlertDetailScreen(alert, position)
    }

    override fun onAllowLocationClicked() {
        view.requestLocationPermission()
    }

    override fun resume() {
        view.refreshList()
    }

}