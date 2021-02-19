package com.tragicfruit.kindweather.ui.home.alertlist

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tragicfruit.kindweather.components.AlertCell
import com.tragicfruit.kindweather.model.WeatherAlert

class AlertListAdapter(
    private val listener: AlertCell.Listener
) : ListAdapter<WeatherAlert, AlertViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlertViewHolder {
        return AlertViewHolder(AlertCell(parent.context))
    }

    override fun onBindViewHolder(holder: AlertViewHolder, position: Int) {
        val alert = getItem(position)
        val cell = holder.itemView as AlertCell
        cell.setData(alert, listener)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<WeatherAlert>() {
            override fun areItemsTheSame(oldItem: WeatherAlert, newItem: WeatherAlert): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: WeatherAlert, newItem: WeatherAlert): Boolean {
                return oldItem.enabled == newItem.enabled
            }
        }
    }
}

class AlertViewHolder(itemView: AlertCell) : RecyclerView.ViewHolder(itemView)