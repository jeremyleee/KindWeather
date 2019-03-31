package com.tragicfruit.kindweather.screens.home.fragments.alertdetail

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
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
        val context = context ?: return
        val color = ContextCompat.getColor(context, alert.getInfo().color)

        // Header
        applyStatusBarColor(ColorHelper.darkenColor(color), lightStatusBar)

        alertDetailCollapsingToolbar.title = getString(alert.getInfo().shortTitle)
        alertDetailCollapsingToolbar.setCollapsedTitleTypeface(ResourcesCompat.getFont(context, R.font.lato_bold))
        alertDetailCollapsingToolbar.setExpandedTitleTypeface(ResourcesCompat.getFont(context, R.font.lato_bold))
        alertDetailCollapsingToolbar.setContentScrimColor(color)

        alertDetailHeader.setBackgroundColor(color)
        alertDetailImage.setImageResource(alert.getInfo().image)

        val gradient = GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, intArrayOf(color, Color.TRANSPARENT))
        alertDetailImageOverlay.setImageDrawable(gradient)

        // Content
        val states = arrayOf(intArrayOf(-android.R.attr.state_checked), intArrayOf(android.R.attr.state_checked))
        val thumbColors = intArrayOf(ContextCompat.getColor(context, R.color.switch_unchecked), color)
        val trackColors = intArrayOf(ContextCompat.getColor(context, R.color.switch_unchecked_track), ColorHelper.darkenColor(color, 0.8f))
        alertDetailEnableSwitch.thumbDrawable.setTintList(ColorStateList(states, thumbColors))
        alertDetailEnableSwitch.trackDrawable.setTintList(ColorStateList(states, trackColors))
        alertDetailEnableSwitch.background = null

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