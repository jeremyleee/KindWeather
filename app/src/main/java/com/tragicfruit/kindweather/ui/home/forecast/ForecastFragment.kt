package com.tragicfruit.kindweather.ui.home.forecast

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.tragicfruit.kindweather.api.ForecastIcon
import com.tragicfruit.kindweather.databinding.FragmentForecastBinding
import com.tragicfruit.kindweather.model.ForecastType
import com.tragicfruit.kindweather.ui.WFragment
import com.tragicfruit.kindweather.utils.ColorHelper
import com.tragicfruit.kindweather.utils.DisplayUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForecastFragment : WFragment() {

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
        viewModel.mainColor.observe(viewLifecycleOwner) { color ->
            binding.toolbar.setBackgroundColor(color)
            applyStatusBarColor(ColorHelper.darkenColor(color), lightStatusBar)
        }

        viewModel.createdAt.observe(viewLifecycleOwner) { createdAt ->
            binding.toolbar.title = DisplayUtils.getDateString(createdAt)
        }

        viewModel.forecast.observe(viewLifecycleOwner) { forecast ->
            binding.mainImage.setImageResource(ForecastIcon.fromString(forecast.icon).iconRes)
            binding.highTempValueText.text = forecast.getDataForType(ForecastType.TEMP_HIGH)?.getDisplayString(viewModel.useImperialUnits)
            binding.lowTempValueText.text = forecast.getDataForType(ForecastType.TEMP_LOW)?.getDisplayString(viewModel.useImperialUnits)
            binding.precipValueText.text = forecast.getDataForType(ForecastType.PRECIP_PROBABILITY)?.getDisplayString(viewModel.useImperialUnits)

            viewModel.fetchAddress(view.context, forecast)
        }

        viewModel.addressString.observe(viewLifecycleOwner) { address ->
            binding.addressText.text = address
        }

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }
}