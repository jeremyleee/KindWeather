package com.tragicfruit.weatherapp.screens.home.fragments.feed

import com.tragicfruit.weatherapp.model.WeatherNotification
import com.tragicfruit.weatherapp.screens.WPresenter
import com.tragicfruit.weatherapp.screens.WView
import io.realm.RealmResults

interface FeedContract {

    interface View : WView {
        fun refreshFeed()
        fun initView(feedData: RealmResults<WeatherNotification>)
    }

    interface Presenter : WPresenter<View> {
        fun init()
    }

}