package com.tragicfruit.kindweather.screens.home.fragments.alertdetail

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.tragicfruit.kindweather.R
import com.tragicfruit.kindweather.components.AlertDetailParamView
import com.tragicfruit.kindweather.databinding.FragmentAlertDetailBinding
import com.tragicfruit.kindweather.model.WeatherAlert
import com.tragicfruit.kindweather.model.WeatherAlertParam
import com.tragicfruit.kindweather.screens.WFragment
import com.tragicfruit.kindweather.utils.ColorHelper

class AlertDetailFragment : WFragment(), AlertDetailContract.View, AlertDetailParamView.Listener {

    private val presenter = AlertDetailPresenter(this)

    private val args: AlertDetailFragmentArgs by navArgs()

    private var _binding: FragmentAlertDetailBinding? = null
    private val binding get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlertDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.init(args.alertId)

        binding.toolbar.setNavigationOnClickListener {
            presenter.onBackClicked()
        }

        binding.enableSwitch.setOnCheckedChangeListener { _, isChecked ->
            presenter.onAlertEnabled(isChecked)
        }

        binding.resetButton.setOnClickListener {
            presenter.onResetToDefaultClicked()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun initView(alert: WeatherAlert) {
        val context = context ?: return
        val color = ContextCompat.getColor(context, alert.getInfo().color)

        initHeaderView(alert, color)
        initContentViews(alert, color, context)
        initParamList(alert)
    }

    private fun initHeaderView(alert: WeatherAlert, @ColorInt color: Int) {
        applyStatusBarColor(ColorHelper.darkenColor(color), lightStatusBar)

        binding.collapsingToolbar.apply {
            title = getString(alert.getInfo().shortTitle)
            setCollapsedTitleTypeface(ResourcesCompat.getFont(context, R.font.playfair_bold))
            setExpandedTitleTypeface(ResourcesCompat.getFont(context, R.font.playfair_bold))
            setContentScrimColor(color)
        }

        binding.header.setBackgroundColor(color)
        Glide.with(this)
            .load(alert.getInfo().image)
            .centerCrop()
            .into(binding.headerImage)

        val gradient = GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, intArrayOf(color, Color.TRANSPARENT))
        binding.headerImageOverlay.setImageDrawable(gradient)
    }

    private fun initContentViews(alert: WeatherAlert, @ColorInt color: Int, context: Context) {
        val states = arrayOf(intArrayOf(-android.R.attr.state_checked), intArrayOf(android.R.attr.state_checked))
        val thumbColors = intArrayOf(ContextCompat.getColor(context, R.color.switch_unchecked), color)
        val trackColors = intArrayOf(ContextCompat.getColor(context, R.color.switch_unchecked_track), ColorHelper.darkenColor(color, 0.8f))
        binding.enableSwitch.apply {
            thumbDrawable.setTintList(ColorStateList(states, thumbColors))
            trackDrawable.setTintList(ColorStateList(states, trackColors))
            background = null
            isChecked = alert.enabled
        }

        binding.resetButton.isEnabled = alert.areParamsEdited()
    }

    private fun initParamList(alert: WeatherAlert) {
        binding.paramsTitle.isVisible = alert.params.isNotEmpty()
        binding.paramsList.removeAllViews()
        alert.params.forEach { param ->
            val paramView = AlertDetailParamView(binding.paramsList.context)
            paramView.setData(ContextCompat.getColor(paramView.context, alert.getInfo().color), param, this)
            binding.paramsList.addView(paramView)
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
        binding.resetButton.isEnabled = enabled
    }

    override fun closeScreen() {
        findNavController().navigateUp()
    }

}