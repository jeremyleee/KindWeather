package com.tragicfruit.kindweather.ui.home.settings

import android.app.TimePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import androidx.fragment.app.viewModels
import com.tragicfruit.kindweather.R
import com.tragicfruit.kindweather.databinding.FragmentSettingsBinding
import com.tragicfruit.kindweather.ui.BaseFragment
import com.tragicfruit.kindweather.ui.home.settings.dialogs.TimePickerDialogFragment
import com.tragicfruit.kindweather.ui.home.settings.dialogs.UnitsDialogFragment
import com.tragicfruit.kindweather.utils.DisplayUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : BaseFragment(), TimePickerDialog.OnTimeSetListener, UnitsDialogFragment.Listener {

    override var statusBarColor = R.color.white

    private val viewModel: SettingsViewModel by viewModels()

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
            binding.unitsItem.setSubtitle(if (useImperial) {
                R.string.settings_units_imperial
            } else {
                R.string.settings_units_metric
            })
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
            val fragment = TimePickerDialogFragment.newInstance(it.hour, it.minute, this)
            fragment.show(parentFragmentManager, fragment.javaClass.name)
        }
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        context?.let {
            viewModel.updateAlertTime(it, hourOfDay, minute)
        }
    }

    private fun showChangeUnitsDialog() {
        viewModel.useImperialUnits.value?.let {
            val units = if (it) {
                UnitsDialogFragment.Units.IMPERIAL
            } else {
                UnitsDialogFragment.Units.METRIC
            }
            val fragment = UnitsDialogFragment.newInstance(units, this)
            fragment.show(parentFragmentManager, fragment.javaClass.name)
        }
    }

    override fun onUnitsChanged(units: UnitsDialogFragment.Units) {
        viewModel.updateUnits(units == UnitsDialogFragment.Units.IMPERIAL)
    }

    private fun openDarkSkyWebPage() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(SettingsViewModel.DARK_SKY_URL))
        startActivity(intent)
    }
}
