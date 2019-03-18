package com.tragicfruit.weatherapp.screens.alert.fragments.list

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.tragicfruit.weatherapp.R
import com.tragicfruit.weatherapp.components.AlertCell
import com.tragicfruit.weatherapp.model.WeatherAlert
import com.tragicfruit.weatherapp.screens.WFragment
import com.tragicfruit.weatherapp.screens.alertdetail.AlertDetailActivity
import com.tragicfruit.weatherapp.screens.settings.SettingsActivity
import com.tragicfruit.weatherapp.utils.PermissionHelper
import kotlinx.android.synthetic.main.fragment_alert_list.*

class AlertListFragment : WFragment(), AlertListContract.View, AlertCell.Listener {

    private val presenter = AlertListPresenter(this)
    private val adapter = AlertListAdapter(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_alert_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        alertListToolbar.inflateMenu(R.menu.menu_alert_list)
        alertListToolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_settings -> {
                    presenter.onSettingsClicked()
                    true
                }
                else -> false
            }
        }

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
        activity?.let {
            val cell = alertListRecyclerView.layoutManager?.findViewByPosition(position) as? AlertCell
            AlertDetailActivity.show(it, alert.id, cell?.backgroundImage)
        }
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

    override fun showSettingsScreen() {
        context?.let {
            SettingsActivity.show(it)
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