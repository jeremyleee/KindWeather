package com.tragicfruit.kindweather.screens.home.fragments.alertdetail

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.tragicfruit.kindweather.R
import com.tragicfruit.kindweather.components.AlertDetailParamView
import com.tragicfruit.kindweather.model.WeatherAlert
import com.tragicfruit.kindweather.model.WeatherAlertParam
import com.tragicfruit.kindweather.screens.WFragment
import com.tragicfruit.kindweather.utils.ColorHelper
import kotlinx.android.synthetic.main.fragment_alert_detail.*
import android.graphics.drawable.GradientDrawable

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
            presenter.onBackClicked()
        }

        alertDetailEnableSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            presenter.onAlertEnabled(isChecked)
        }

        alertDetailReset.setOnClickListener {
            presenter.onResetToDefaultClicked()
        }
    }

    override fun initView(alert: WeatherAlert) {
        alertDetailCollapsingToolbar.title = getString(alert.getInfo().shortTitle)

        val color = context?.let {
            ContextCompat.getColor(it, alert.getInfo().color)
        } ?: Color.WHITE

        applyStatusBarColor(ColorHelper.darkenColor(color), lightStatusBar)
        alertDetailHeader.setBackgroundColor(color)
        alertDetailImage.setImageResource(alert.getInfo().image)
        alertDetailCollapsingToolbar.setContentScrimColor(color)

        val gradient = GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, intArrayOf(color, Color.TRANSPARENT))
        alertDetailImageOverlay.setImageDrawable(gradient)

        context?.let {
            alertDetailCollapsingToolbar.setCollapsedTitleTypeface(ResourcesCompat.getFont(it, R.font.lato_bold))
            alertDetailCollapsingToolbar.setExpandedTitleTypeface(ResourcesCompat.getFont(it, R.font.lato_bold))
        }

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
                paramView.setData(ContextCompat.getColor(it, alert.getInfo().color), param, this)
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
        findNavController().navigateUp()
    }

}