package com.tragicfruit.weatherapp.screens.alert.fragments.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import com.tragicfruit.weatherapp.R
import com.tragicfruit.weatherapp.model.WeatherAlert
import com.tragicfruit.weatherapp.screens.WFragment
import kotlinx.android.synthetic.main.fragment_alert_detail.*

class AlertDetailFragment : WFragment(), AlertDetailContract.View {

    private val presenter = AlertDetailPresenter(this)
    private val args: AlertDetailFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_alert_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.init(args.alertId)

        alertDetailToolbar.setNavigationOnClickListener {
            presenter.onToolbarBackClicked()
        }
    }

    override fun onInitView(alert: WeatherAlert) {
        alertDetailCollapsingToolbar.title = alert.name

        alertDetailHeaderImage.setBackgroundColor(alert.color) // TODO: replace with illustration
        alertDetailCollapsingToolbar.setContentScrimColor(alert.color) // TODO: replace with illustration palette colour

        alertDetailEnableSwitch.isChecked = alert.enabled
        alertDetailEnableSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            presenter.onAlertEnabled(isChecked)
        }
    }

    override fun closeScreen() {
        NavHostFragment.findNavController(this).navigateUp()
    }

}