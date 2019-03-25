package com.tragicfruit.kindweather.screens.home.fragments.feed

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tragicfruit.kindweather.components.FeedCell
import com.tragicfruit.kindweather.model.WeatherNotification
import io.realm.RealmResults

class FeedAdapter(private val feedList: RealmResults<WeatherNotification>,
                  private val listener: FeedCell.Listener) : RecyclerView.Adapter<FeedViewHolder>() {

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        return FeedViewHolder(FeedCell(parent.context, listener))
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        feedList[position]?.let { notification ->
            val cell = holder.itemView as FeedCell
            cell.setData(notification)
        }
    }

    override fun getItemCount() = feedList.count()

}

class FeedViewHolder(itemView: FeedCell) : RecyclerView.ViewHolder(itemView)