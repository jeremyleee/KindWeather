package com.tragicfruit.weatherapp.screens.home.fragments.alertdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.navArgs
import com.tragicfruit.weatherapp.R
import com.tragicfruit.weatherapp.components.AlertDetailParamView
import com.tragicfruit.weatherapp.model.WeatherAlert
import com.tragicfruit.weatherapp.model.WeatherAlertParam
import com.tragicfruit.weatherapp.screens.WFragment
import kotlinx.android.synthetic.main.fragment_alert_detail.*

class AlertDetailFragment : WFragment(), AlertDetailContract.View, AlertDetailParamView.Listener {

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

        alertDetailEnableSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            presenter.onAlertEnabled(isChecked)
        }

        alertDetailReset.setOnClickListener {
            presenter.onResetToDefaultClicked()
        }
    }

    override fun initView(alert: WeatherAlert) {
        alertDetailCollapsingToolbar.title = alert.name

        alertDetailHeaderImage.setBackgroundColor(alert.color) // TODO: replace with illustration
        alertDetailCollapsingToolbar.setContentScrimColor(alert.color) // TODO: replace with illustration palette colour

        alertDetailEnableSwitch.isChecked = alert.enabled
        alertDetailReset.isEnabled = alert.areParamsEdited()

        initParamList(alert)
    }

    private fun initParamList(alert: WeatherAlert) {
        alertDetailParamsTitle.isVisible = alert.params.isNotEmpty()

        context?.let {
            alertDetailParamsList.removeAllViews()
            for (param in alert.params) {
                val paramView = AlertDetailParamView(it)
                paramView.setData(alert.color, param, this)
                alertDetailParamsList.addView(paramView)
            }
        }
    }

    override fun refreshParamList(alert: WeatherAlert) {
        initParamList(alert)
    }

    override fun onLowerBoundChanged(param: WeatherAlertParam, value: Double?) {
        presenter.onLowerBoundChanged(param, value)
    }

    override fun onUpperBoundChanged(param: WeatherAlertParam, value: Double?) {
        presenter.onUpperBoundChanged(param, value)
    }

    override fun setResetButtonEnabled(enabled: Boolean) {
        alertDetailReset.isEnabled = enabled
    }

    override fun closeScreen() {
        activity?.onBackPressed()
    }

}