package com.tragicfruit.weatherapp.screens.alert.fragments.list

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.tragicfruit.weatherapp.R
import com.tragicfruit.weatherapp.model.WeatherAlert
import com.tragicfruit.weatherapp.screens.WFragment
import com.tragicfruit.weatherapp.screens.alert.fragments.detail.AlertDetailFragment
import com.tragicfruit.weatherapp.screens.alert.fragments.list.components.AlertCell
import com.tragicfruit.weatherapp.utils.PermissionHelper
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

        alertListAllowLocation.setButtonClickListener {
            presenter.onAllowLocationClicked()
        }
    }

    override fun onAlertClicked(alert: WeatherAlert) {
        presenter.onAlertClicked(alert)
    }

    override fun showAlertDetailScreen(alert: WeatherAlert) {
        baseActivity?.presentFragment(AlertDetailFragment.newInstance(alert.id))
    }

    override fun requestLocationPermission() {
        requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_LOCATION_PERMISSION)
    }

    override fun onResume() {
        super.onResume()
        presenter.resume()
    }

    override fun onPause() {
        super.onPause()
        presenter.pause()
    }

    override fun refreshList() {
        alertListRecyclerView.adapter?.notifyDataSetChanged()

        context?.let {
            alertListAllowLocation.isVisible = !PermissionHelper.hasPermission(it, Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    companion object {
        private const val REQUEST_LOCATION_PERMISSION = 800
    }

}