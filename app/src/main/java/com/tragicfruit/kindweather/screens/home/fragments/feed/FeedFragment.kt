package com.tragicfruit.kindweather.screens.home.fragments.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.tragicfruit.kindweather.R
import com.tragicfruit.kindweather.components.FeedCell
import com.tragicfruit.kindweather.databinding.FragmentFeedBinding
import com.tragicfruit.kindweather.model.ForecastPeriod
import com.tragicfruit.kindweather.model.WeatherNotification
import com.tragicfruit.kindweather.screens.WFragment
import io.realm.RealmResults

class FeedFragment : WFragment(), FeedContract.View, FeedCell.Listener {

    override var statusBarColor = R.color.white

    private val presenter = FeedPresenter(this)

    private var _binding: FragmentFeedBinding? = null
    private val binding get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFeedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.init()
    }

    override fun initView(feedData: RealmResults<WeatherNotification>) {
        binding.recyclerView.adapter = FeedAdapter(feedData, this)
        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        binding.emptyView.isVisible = feedData.isEmpty()

        Glide.with(this)
            .load(R.drawable.feed_empty)
            .into(binding.emptyImage)

        binding.setupButton.setOnClickListener {
            presenter.onSetupConditionsClicked()
        }
    }

    override fun showForecastScreen(forecast: ForecastPeriod, timeCreatedMillis: Long, color: Int) {
        val action = FeedFragmentDirections.actionFeedFragmentToForecastFragment(forecast.id, timeCreatedMillis, color)
        findNavController().navigate(action)
    }

    override fun showConditionsScreen() {
        findNavController().navigate(R.id.action_feedFragment_to_alertListFragment)
    }

    override fun refreshFeed() {
        binding.recyclerView.adapter?.notifyDataSetChanged()
    }

    override fun showEmptyState(show: Boolean) {
        binding.emptyView.isVisible = show
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