package com.tragicfruit.kindweather.ui.home.fragments.feed

import com.tragicfruit.kindweather.model.WeatherNotification
import io.realm.Realm
import io.realm.RealmChangeListener
import io.realm.RealmResults
import io.realm.Sort
import io.realm.kotlin.where
import java.util.concurrent.TimeUnit

class FeedPresenter(override var view: FeedContract.View) : FeedContract.Presenter,
    RealmChangeListener<RealmResults<WeatherNotification>> {

    private lateinit var feedData: RealmResults<WeatherNotification>

    override fun init() {
        feedData = Realm.getDefaultInstance()
            .where<WeatherNotification>()
            .sort("createdAt", Sort.DESCENDING)
            .findAll()

        view.initView(feedData)
    }

    override fun onFeedItemClicked(notification: WeatherNotification) {
        notification.forecast?.let {
            val timeCreatedMillis = notification.createdAt?.time ?: TimeUnit.SECONDS.toMillis(it.fetchedTime)
            view.showForecastScreen(it, timeCreatedMillis, notification.color)
        }
    }

    override fun onSetupConditionsClicked() {
        view.showConditionsScreen()
    }

    override fun resume() {
        updateFeedView()
        feedData.addChangeListener(this)
    }

    override fun pause() {
        feedData.removeChangeListener(this)
    }

    override fun onChange(t: RealmResults<WeatherNotification>) {
        updateFeedView()
    }

    private fun updateFeedView() {
        view.refreshFeed()
        view.showEmptyState(feedData.isEmpty())
    }

}