package com.tragicfruit.kindweather.ui.home.alertdetail

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
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.tragicfruit.kindweather.R
import com.tragicfruit.kindweather.data.WeatherAlert
import com.tragicfruit.kindweather.data.WeatherAlertParam
import com.tragicfruit.kindweather.databinding.FragmentAlertDetailBinding
import com.tragicfruit.kindweather.ui.BaseFragment
import com.tragicfruit.kindweather.ui.components.AlertDetailParamView
import com.tragicfruit.kindweather.utils.ColorHelper
import com.tragicfruit.kindweather.utils.SharedPrefsHelper
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AlertDetailFragment : BaseFragment(), AlertDetailParamView.Listener {

    @Inject lateinit var sharedPrefsHelper: SharedPrefsHelper

    private val viewModel: AlertDetailViewModel by viewModels()

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
        viewModel.alertWithParams.observe(viewLifecycleOwner) {
            val color = ContextCompat.getColor(view.context, it.alert.alertType.color)
            setupHeaderView(it.alert, color)
            setupContentViews(it.alert, color, view.context)
            updateParamListView(it.params, color)
        }

        viewModel.resetButtonEnabled.observe(viewLifecycleOwner) { enabled ->
            binding.resetButton.isEnabled = enabled
        }

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        binding.enableSwitch.setOnCheckedChangeListener { _, isChecked ->
            viewModel.enableAlert(isChecked)
        }

        binding.resetButton.setOnClickListener {
            viewModel.resetParams()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupHeaderView(alert: WeatherAlert, @ColorInt color: Int) {
        applyStatusBarColor(ColorHelper.darkenColor(color), lightStatusBar)

        binding.collapsingToolbar.apply {
            title = getString(alert.alertType.shortTitle)
            setCollapsedTitleTypeface(ResourcesCompat.getFont(context, R.font.playfair_bold))
            setExpandedTitleTypeface(ResourcesCompat.getFont(context, R.font.playfair_bold))
            setContentScrimColor(color)
        }

        binding.header.setBackgroundColor(color)
        Glide.with(this)
            .load(alert.alertType.image)
            .centerCrop()
            .into(binding.headerImage)

        val gradient = GradientDrawable(
            GradientDrawable.Orientation.BOTTOM_TOP,
            intArrayOf(color, Color.TRANSPARENT)
        )
        binding.headerImageOverlay.setImageDrawable(gradient)
    }

    private fun setupContentViews(alert: WeatherAlert, @ColorInt color: Int, context: Context) {
        val states = arrayOf(
            intArrayOf(-android.R.attr.state_checked),
            intArrayOf(android.R.attr.state_checked)
        )
        val thumbColors = intArrayOf(
            ContextCompat.getColor(context, R.color.switch_unchecked),
            color
        )
        val trackColors = intArrayOf(
            ContextCompat.getColor(context, R.color.switch_unchecked_track),
            ColorHelper.darkenColor(color, 0.8f)
        )
        binding.enableSwitch.apply {
            thumbDrawable.setTintList(ColorStateList(states, thumbColors))
            trackDrawable.setTintList(ColorStateList(states, trackColors))
            background = null
            isChecked = alert.enabled
            jumpDrawablesToCurrentState()
        }
    }

    private fun updateParamListView(params: List<WeatherAlertParam>, @ColorInt color: Int) {
        binding.paramsTitle.isVisible = params.isNotEmpty()
        binding.paramsList.removeAllViews()
        params.forEach { param ->
            val paramView = AlertDetailParamView(binding.paramsList.context)
            paramView.setData(color, param, sharedPrefsHelper.usesImperialUnits(), this)
            binding.paramsList.addView(paramView)
        }
    }

    override fun onLowerBoundChanged(param: WeatherAlertParam, value: Double?) {
        viewModel.updateLowerBound(param, value)
    }

    override fun onUpperBoundChanged(param: WeatherAlertParam, value: Double?) {
        viewModel.updateUpperBound(param, value)
    }
}
