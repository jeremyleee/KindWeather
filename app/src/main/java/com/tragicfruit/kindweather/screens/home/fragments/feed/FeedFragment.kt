package com.tragicfruit.kindweather.screens.home.fragments.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tragicfruit.kindweather.R
import com.tragicfruit.kindweather.components.FeedCell
import com.tragicfruit.kindweather.model.ForecastPeriod
import com.tragicfruit.kindweather.model.WeatherNotification
import com.tragicfruit.kindweather.screens.WFragment
import io.realm.RealmResults
import kotlinx.android.synthetic.main.fragment_feed.*

class FeedFragment : WFragment(), FeedContract.View, FeedCell.Listener {

    private val presenter = FeedPresenter(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.init()
    }

    override fun initView(feedData: RealmResults<WeatherNotification>) {
        feedRecyclerView.adapter = FeedAdapter(feedData, this)
        feedRecyclerView.layoutManager = LinearLayoutManager(context)
    }

    override fun showForecastScreen(forecast: ForecastPeriod) {
        val action = FeedFragmentDirections.actionFeedFragmentToForecastFragment(forecast.id)
        findNavController().navigate(action)
    }

    override fun refreshFeed() {
        feedRecyclerView.adapter?.notifyDataSetChanged()
    }

    override fun onFeedItemClicked(notification: WeatherNotification) {
        presenter.onFeedItemClicked(notification)
    }

    override fun onResume() {
        super.onResume()
        presenter.resume()
    }

    override fun onPause() {
        super.onPause()
        presenter.pause()
    }

}