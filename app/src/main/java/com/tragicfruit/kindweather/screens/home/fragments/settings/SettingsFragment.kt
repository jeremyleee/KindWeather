package com.tragicfruit.kindweather.screens.home.fragments.settings

import android.app.TimePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import com.tragicfruit.kindweather.R
import com.tragicfruit.kindweather.controllers.AlertController
import com.tragicfruit.kindweather.databinding.FragmentSettingsBinding
import com.tragicfruit.kindweather.screens.WFragment
import com.tragicfruit.kindweather.screens.home.fragments.settings.dialogs.TimePickerDialogFragment
import com.tragicfruit.kindweather.screens.home.fragments.settings.dialogs.UnitsDialogFragment
import com.tragicfruit.kindweather.utils.DisplayUtils

class SettingsFragment : WFragment(), SettingsContract.View, TimePickerDialog.OnTimeSetListener, UnitsDialogFragment.Listener {

    override var statusBarColor = R.color.white

    private val presenter = SettingsPresenter(this)

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
        presenter.init()

        binding.alertTimeItem.setOnClickListener {
            presenter.onAlertTimeClicked()
        }

        binding.unitsItem.setOnClickListener {
            presenter.onUnitsClicked()
        }

        binding.darkSkyDisclaimer.setOnClickListener {
            presenter.onDarkSkyDisclaimerClicked()
        }
    }

    override fun showAlertTimeDialog(initialAlertHour: Int, initialAlertMinute: Int) {
        val fragment = TimePickerDialogFragment.newInstance(initialAlertHour, initialAlertMinute, this)
        fragment.show(parentFragmentManager, fragment.javaClass.name)
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        presenter.onAlertTimeChanged(hourOfDay, minute)
    }

    override fun updateAlertTimeText(alertHour: Int, alertMinute: Int) {
        binding.alertTimeItem.setSubtitle(DisplayUtils.getTimeString(alertHour, alertMinute, 0))
    }

    override fun updateUnitsText(usesImperial: Boolean) {
        val units = getString(if (usesImperial) R.string.settings_units_imperial else R.string.settings_units_metric)
        binding.unitsItem.setSubtitle(units)
    }

    override fun restartAlertService() {
        context?.let {
            AlertController.scheduleDailyAlert(it)
        }
    }

    override fun showChangeUnitsDialog(usesImperial: Boolean) {
        val units = if (usesImperial) UnitsDialogFragment.Units.IMPERIAL else UnitsDialogFragment.Units.METRIC
        val fragment = UnitsDialogFragment.newInstance(units, this)
        fragment.show(parentFragmentManager, fragment.javaClass.name)
    }

    override fun onUnitsChanged(units: UnitsDialogFragment.Units) {
        presenter.onUnitsChanged(units == UnitsDialogFragment.Units.IMPERIAL)
    }

    override fun openWebPage(url: String) {
        context?.let {
            val webpage = Uri.parse(url)
            val intent = Intent(Intent.ACTION_VIEW, webpage)
            if (intent.resolveActivity(it.packageManager) != null) {
                startActivity(intent)
            }
        }
    }

}