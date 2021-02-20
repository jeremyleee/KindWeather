package com.tragicfruit.kindweather.ui.home.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.tragicfruit.kindweather.R
import com.tragicfruit.kindweather.components.FeedCell
import com.tragicfruit.kindweather.databinding.FragmentFeedBinding
import com.tragicfruit.kindweather.model.WeatherNotification
import com.tragicfruit.kindweather.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class FeedFragment : BaseFragment(), FeedCell.Listener {

    override var statusBarColor = R.color.white

    private val viewModel: FeedViewModel by viewModels()
    private var adapter = FeedAdapter(this)

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
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        Glide.with(this)
            .load(R.drawable.feed_empty)
            .into(binding.emptyImage)

        viewModel.feedData.observe(viewLifecycleOwner) {
            adapter.submitList(it)
            binding.emptyView.isVisible = it.isEmpty()
        }

        binding.setupButton.setOnClickListener {
            findNavController().navigate(R.id.action_feedFragment_to_alertListFragment)
        }
    }

    override fun onFeedItemClicked(notification: WeatherNotification) {
        notification.forecast?.let {
            val timeCreatedMillis = notification.createdAt?.time ?: TimeUnit.SECONDS.toMillis(it.fetchedTime)
            val action = FeedFragmentDirections
                .actionFeedFragmentToForecastFragment(it.id, timeCreatedMillis, notification.color)
            findNavController().navigate(action)
        }
    }
}