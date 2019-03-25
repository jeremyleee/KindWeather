package com.tragicfruit.kindweather.screens.home.fragments.alertlist

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tragicfruit.kindweather.R
import com.tragicfruit.kindweather.components.AlertCell
import com.tragicfruit.kindweather.model.WeatherAlert
import com.tragicfruit.kindweather.screens.WFragment
import com.tragicfruit.kindweather.utils.PermissionHelper
import kotlinx.android.synthetic.main.fragment_alert_list.*

class AlertListFragment : WFragment(), AlertListContract.View, AlertCell.Listener {

    override var statusBarColor = R.color.alert_list_background

    private val presenter = AlertListPresenter(this)
    private val adapter = AlertListAdapter(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_alert_list, container, false)
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
        // TODO: shared element transition
//        val cell = alertListRecyclerView.layoutManager?.findViewByPosition(position) as? AlertCell

        val action = AlertListFragmentDirections.actionAlertsFragmentToAlertDetailFragment(alert.id)
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