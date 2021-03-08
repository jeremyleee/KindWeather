package com.tragicfruit.kindweather.ui.home.forecast

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.tragicfruit.kindweather.databinding.FragmentForecastBinding
import com.tragicfruit.kindweather.ui.BaseFragment
import com.tragicfruit.kindweather.utils.ColorHelper
import com.tragicfruit.kindweather.utils.DisplayUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForecastFragment : BaseFragment() {

    private val viewModel: ForecastViewModel by viewModels()

    private var _binding: FragmentForecastBinding? = null
    private val binding get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentForecastBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.notification.observe(viewLifecycleOwner) {
            binding.toolbar.setBackgroundColor(it.color)
            applyStatusBarColor(ColorHelper.darkenColor(it.color), lightStatusBar)

            binding.toolbar.title = DisplayUtils.getDateString(it.createdAt)

            binding.mainImage.setImageResource(it.forecastIcon.iconRes)
            binding.highTempValueText.text = it.getTempHighString(viewModel.useImperialUnits)
            binding.lowTempValueText.text = it.getTempLowString(viewModel.useImperialUnits)
            binding.precipValueText.text = it.getPrecipProbabilityString(viewModel.useImperialUnits)
        }

        viewModel.addressLabel.observe(viewLifecycleOwner) { address ->
            binding.addressText.text = address
        }

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }
}
