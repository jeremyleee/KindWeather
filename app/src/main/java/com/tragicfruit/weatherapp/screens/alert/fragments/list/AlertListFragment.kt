package com.tragicfruit.weatherapp.screens.alert.fragments.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.tragicfruit.weatherapp.R
import com.tragicfruit.weatherapp.screens.WFragment
import kotlinx.android.synthetic.main.fragment_alert_list.*

class AlertListFragment : WFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_alert_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        alertListRecyclerView.adapter = AlertListAdapter()
        alertListRecyclerView.layoutManager = LinearLayoutManager(context)
    }

}