package com.tragicfruit.kindweather.ui.home.feed

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tragicfruit.kindweather.components.FeedCell
import com.tragicfruit.kindweather.model.WeatherNotification

class FeedAdapter(
    private val listener: FeedCell.Listener
) : ListAdapter<WeatherNotification, FeedViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        return FeedViewHolder(FeedCell(parent.context))
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        val notification = getItem(position)
        val cell = holder.itemView as FeedCell
        cell.setData(notification, listener)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<WeatherNotification>() {
            override fun areItemsTheSame(oldItem: WeatherNotification, newItem: WeatherNotification): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: WeatherNotification, newItem: WeatherNotification): Boolean {
                return oldItem.createdAt == newItem.createdAt
                        && oldItem.description == newItem.description
                        && oldItem.forecastIcon == newItem.forecastIcon
                        && oldItem.rawTempHigh == newItem.rawTempHigh
            }
        }
    }
}

class FeedViewHolder(itemView: FeedCell) : RecyclerView.ViewHolder(itemView)