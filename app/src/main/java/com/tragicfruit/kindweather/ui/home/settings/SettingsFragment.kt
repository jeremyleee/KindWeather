package com.tragicfruit.kindweather.ui.home.settings

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import com.tragicfruit.kindweather.R
import com.tragicfruit.kindweather.databinding.FragmentSettingsBinding
import com.tragicfruit.kindweather.ui.BaseFragment
import com.tragicfruit.kindweather.ui.home.settings.dialogs.TimePickerDialogFragment
import com.tragicfruit.kindweather.ui.home.settings.dialogs.UnitsDialogFragment
import com.tragicfruit.kindweather.util.DisplayUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : BaseFragment() {

    override var statusBarColor = R.color.white

    private val viewModel: SettingsViewModel by viewModels()

    private val fragmentResultListener: (String, Bundle) -> Unit = { requestKey, bundle ->
        when (requestKey) {
            UnitsDialogFragment.REQUEST_UPDATE_UNITS -> {
                val updatedUnit = bundle.get(UnitsDialogFragment.KEY_UNIT)
                viewModel.updateUnits(updatedUnit == UnitsDialogFragment.Units.IMPERIAL)
            }
            TimePickerDialogFragment.REQUEST_UPDATE_TIME -> {
                val hourOfDay = bundle.getInt(TimePickerDialogFragment.KEY_HOUR)
                val minute = bundle.getInt(TimePickerDialogFragment.KEY_MINUTE)
                context?.let {
                    viewModel.updateAlertTime(it, hourOfDay, minute)
                }
            }
        }
    }

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.alertTime.observe(viewLifecycleOwner) { time ->
            binding.alertTimeItem.setSubtitle(DisplayUtils.getTimeString(time.hour, time.minute, 0))
        }

        viewModel.useImperialUnits.observe(viewLifecycleOwner) { useImperial ->
            binding.unitsItem.setSubtitle(
                if (useImperial) {
                    R.string.settings_units_imperial
                } else {
                    R.string.settings_units_metric
                }
            )
        }

        with(fragmentResultListener) {
            setFragmentResultListener(UnitsDialogFragment.REQUEST_UPDATE_UNITS, this)
            setFragmentResultListener(TimePickerDialogFragment.REQUEST_UPDATE_TIME, this)
        }

        binding.alertTimeItem.setOnClickListener {
            showAlertTimeDialog()
        }

        binding.unitsItem.setOnClickListener {
            showChangeUnitsDialog()
        }

        binding.darkSkyDisclaimer.setOnClickListener {
            openDarkSkyWebPage()
        }
    }

    private fun showAlertTimeDialog() {
        viewModel.alertTime.value?.let {
            val fragment = TimePickerDialogFragment.newInstance(it.hour, it.minute)
            fragment.show(parentFragmentManager, fragment.javaClass.name)
        }
    }

    private fun showChangeUnitsDialog() {
        viewModel.useImperialUnits.value?.let {
            val units = if (it) {
                UnitsDialogFragment.Units.IMPERIAL
            } else {
                UnitsDialogFragment.Units.METRIC
            }
            val fragment = UnitsDialogFragment.newInstance(units)
            fragment.show(parentFragmentManager, fragment.javaClass.name)
        }
    }

    private fun openDarkSkyWebPage() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(SettingsViewModel.DARK_SKY_URL))
        startActivity(intent)
    }
}
