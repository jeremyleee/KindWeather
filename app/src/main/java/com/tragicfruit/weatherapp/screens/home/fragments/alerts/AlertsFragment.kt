package com.tragicfruit.weatherapp.screens.home.fragments.alerts

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tragicfruit.weatherapp.R
import com.tragicfruit.weatherapp.components.AlertCell
import com.tragicfruit.weatherapp.model.WeatherAlert
import com.tragicfruit.weatherapp.screens.WFragment
import com.tragicfruit.weatherapp.utils.PermissionHelper
import kotlinx.android.synthetic.main.fragment_alerts.*

class AlertsFragment : WFragment(), AlertsContract.View, AlertCell.Listener {

    private val presenter = AlertsPresenter(this)
    private val adapter = AlertListAdapter(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_alerts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        alertListRecyclerView.adapter = adapter
        alertListRecyclerView.layoutManager = LinearLayoutManager(context)

        alertListAllowLocation.setButtonClickListener {
            presenter.onAllowLocationClicked()
        }
    }

    override fun onAlertClicked(alert: WeatherAlert) {
        val position = adapter.getItemPosition(alert)
        presenter.onAlertClicked(alert, position)
    }

    override fun showAlertDetailScreen(alert: WeatherAlert, position: Int) {
//        val cell = alertListRecyclerView.layoutManager?.findViewByPosition(position) as? AlertCell

        val action = AlertsFragmentDirections.actionAlertsFragmentToAlertDetailFragment(alert.id)
        findNavController().navigate(action)
    }

    override fun requestLocationPermission() {
        requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_LOCATION_PERMISSION)
    }

    override fun refreshList() {
        adapter.notifyDataSetChanged()

        context?.let {
            alertListAllowLocation.isVisible = !PermissionHelper.hasPermission(it, Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.resume()
    }

    override fun onPause() {
        super.onPause()
        presenter.pause()
    }

    companion object {
        private const val REQUEST_LOCATION_PERMISSION = 800
    }

}