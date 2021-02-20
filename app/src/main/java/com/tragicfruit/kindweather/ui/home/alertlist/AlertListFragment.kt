package com.tragicfruit.kindweather.ui.home.alertlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tragicfruit.kindweather.R
import com.tragicfruit.kindweather.components.AlertCell
import com.tragicfruit.kindweather.databinding.FragmentAlertListBinding
import com.tragicfruit.kindweather.model.WeatherAlert
import com.tragicfruit.kindweather.ui.WFragment
import com.tragicfruit.kindweather.ui.home.requestlocation.RequestLocationDialogFragment
import com.tragicfruit.kindweather.utils.PermissionHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlertListFragment : WFragment(), AlertCell.Listener {

    override var statusBarColor = R.color.white

    private val viewModel: AlertListViewModel by viewModels()
    private val adapter = AlertListAdapter(this)

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
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        viewModel.alertList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        binding.allowLocationButton.setOnClickListener {
            val dialog = RequestLocationDialogFragment()
            dialog.show(childFragmentManager, RequestLocationDialogFragment.TAG)
        }
    }

    override fun onResume() {
        super.onResume()
        binding.allowLocationHeader.isVisible =
            !PermissionHelper.hasBackgroundLocationPermission(context)
    }

    override fun onAlertClicked(alert: WeatherAlert) {
        // TODO: shared element transition
//        val cell = alertListRecyclerView.layoutManager?.findViewByPosition(position) as? AlertCell

        val action = AlertListFragmentDirections.actionAlertsFragmentToAlertDetailFragment(alert.id)
        findNavController().navigate(action)
    }
}