package com.tragicfruit.weatherapp.screens.alert.fragments.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.tragicfruit.weatherapp.R
import com.tragicfruit.weatherapp.model.WeatherAlert
import com.tragicfruit.weatherapp.screens.WFragment
import com.tragicfruit.weatherapp.screens.alert.fragments.detail.AlertDetailFragment
import com.tragicfruit.weatherapp.screens.alert.fragments.list.components.AlertCell
import kotlinx.android.synthetic.main.fragment_alert_list.*

class AlertListFragment : WFragment(), AlertListContract.View, AlertCell.Listener {

    private val presenter = AlertListPresenter(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_alert_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        alertListRecyclerView.adapter = AlertListAdapter(this)
        alertListRecyclerView.layoutManager = LinearLayoutManager(context)
    }

    override fun onAlertClicked(alert: WeatherAlert) {
        presenter.onAlertClicked(alert)
    }

    override fun showAlertDetailScreen(alert: WeatherAlert) {
        baseActivity?.presentFragment(AlertDetailFragment.newInstance(alert.id))
    }

    override fun onStart() {
        super.onStart()
        presenter.start()
    }

    override fun onStop() {
        super.onStop()
        presenter.stop()
    }

    override fun refreshList() {
        alertListRecyclerView.adapter?.notifyDataSetChanged()
    }

}