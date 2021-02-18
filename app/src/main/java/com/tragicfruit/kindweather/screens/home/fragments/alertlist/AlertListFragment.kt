package com.tragicfruit.kindweather.screens.home.fragments.alertlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tragicfruit.kindweather.R
import com.tragicfruit.kindweather.components.AlertCell
import com.tragicfruit.kindweather.databinding.FragmentAlertListBinding
import com.tragicfruit.kindweather.model.WeatherAlert
import com.tragicfruit.kindweather.screens.WFragment
import com.tragicfruit.kindweather.screens.home.fragments.requestlocation.RequestLocationDialogFragment
import com.tragicfruit.kindweather.utils.PermissionHelper
import io.realm.RealmResults

class AlertListFragment : WFragment(), AlertListContract.View, AlertCell.Listener {

    override var statusBarColor = R.color.white

    private val presenter = AlertListPresenter(this)
    private lateinit var adapter: AlertListAdapter

    private var _binding: FragmentAlertListBinding? = null
    private val binding get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlertListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.init()
    }

    override fun initView(alertList: RealmResults<WeatherAlert>) {
        adapter = AlertListAdapter(alertList, this)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        binding.allowLocationButton.setOnClickListener {
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

    override fun requestBackgroundLocationPermission() {
        val dialog = RequestLocationDialogFragment()
        dialog.show(childFragmentManager, RequestLocationDialogFragment.TAG)
    }

    override fun refreshList() {
        adapter.notifyDataSetChanged()

        context?.let {
            binding.allowLocationHeader.isVisible = !PermissionHelper.hasBackgroundLocationPermission(it)
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
}