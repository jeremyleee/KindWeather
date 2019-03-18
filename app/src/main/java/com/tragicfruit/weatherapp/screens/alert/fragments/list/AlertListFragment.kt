package com.tragicfruit.weatherapp.screens.alert.fragments.list

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.Fade
import androidx.transition.TransitionInflater
import androidx.transition.TransitionSet
import com.tragicfruit.weatherapp.R
import com.tragicfruit.weatherapp.model.WeatherAlert
import com.tragicfruit.weatherapp.screens.WFragment
import com.tragicfruit.weatherapp.screens.alert.fragments.detail.AlertDetailFragment
import com.tragicfruit.weatherapp.screens.alert.fragments.list.components.AlertCell
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
        val transitionDuration = 300L

        // Shared element transition
        val enterTransitionSet = TransitionSet()
        enterTransitionSet.addTransition(TransitionInflater.from(context).inflateTransition(android.R.transition.move))
        enterTransitionSet.duration = transitionDuration

        val alertDetailFragment = AlertDetailFragment.newInstance(alert.id).apply {
            sharedElementEnterTransition = enterTransitionSet
        }

        // Enter/exit transitions
        val transition = Fade().apply { duration = transitionDuration }
        this.exitTransition = transition
        alertDetailFragment.enterTransition = transition

        // Present fragment
        val cell = alertListRecyclerView.layoutManager?.findViewByPosition(position) as? AlertCell
        baseActivity?.presentFragment(alertDetailFragment, true, cell?.backgroundImage)
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
        activity?.let {
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