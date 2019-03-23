package com.tragicfruit.kindweather.screens.home.fragments.alertlist

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tragicfruit.kindweather.components.AlertCell
import com.tragicfruit.kindweather.model.WeatherAlert
import io.realm.Realm
import io.realm.RealmResults
import io.realm.Sort
import io.realm.kotlin.where

class AlertListAdapter(private val listener: AlertCell.Listener) : RecyclerView.Adapter<AlertViewHolder>() {

    private val alertList: RealmResults<WeatherAlert>

    init {
        setHasStableIds(true)

        alertList = Realm.getDefaultInstance()
            .where<WeatherAlert>()
            .sort("enabled", Sort.DESCENDING, "priority", Sort.ASCENDING)
            .findAll()
    }

    fun getItemPosition(alert: WeatherAlert): Int {
        return alertList.indexOf(alert)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlertViewHolder {
        return AlertViewHolder(AlertCell(parent.context, listener))
    }

    override fun onBindViewHolder(holder: AlertViewHolder, position: Int) {
        alertList[position]?.let { alert ->
            val cell = holder.itemView as? AlertCell
            cell?.setData(alert)
        }
    }

    override fun getItemCount() = alertList.count()

}

class AlertViewHolder(itemView: AlertCell) : RecyclerView.ViewHolder(itemView)