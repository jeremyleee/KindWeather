package com.tragicfruit.weatherapp.screens.alert.fragments.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.tragicfruit.weatherapp.R
import com.tragicfruit.weatherapp.model.WeatherAlert
import com.tragicfruit.weatherapp.model.WeatherAlertParam
import com.tragicfruit.weatherapp.screens.WFragment
import com.tragicfruit.weatherapp.screens.alert.fragments.detail.components.AlertDetailParamView
import kotlinx.android.synthetic.main.fragment_alert_detail.*

class AlertDetailFragment : WFragment(), AlertDetailContract.View, AlertDetailParamView.Listener {

    private val presenter = AlertDetailPresenter(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_alert_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.init(arguments?.getString(ALERT_ID_EXTRA))

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

    override fun onInitView(alert: WeatherAlert) {
        alertDetailCollapsingToolbar.title = alert.name

        alertDetailHeaderImage.setBackgroundColor(alert.color) // TODO: replace with illustration
        alertDetailCollapsingToolbar.setContentScrimColor(alert.color) // TODO: replace with illustration palette colour

        alertDetailEnableSwitch.isChecked = alert.enabled
        alertDetailReset.isEnabled = alert.areParamsEdited()

        alertDetailHeaderImage.transitionName = "transition_${alert.id}"

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

    companion object {
        const val ALERT_ID_EXTRA = "alert-id"

        fun newInstance(alertId: String): AlertDetailFragment {
            val fragment = AlertDetailFragment()
            fragment.arguments = Bundle().apply {
                putString(ALERT_ID_EXTRA, alertId)
            }
            return fragment
        }
    }

}