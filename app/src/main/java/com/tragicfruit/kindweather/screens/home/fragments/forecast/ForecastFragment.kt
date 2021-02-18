package com.tragicfruit.kindweather.screens.home.fragments.forecast

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.ResultReceiver
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.tragicfruit.kindweather.controllers.FetchAddressService
import com.tragicfruit.kindweather.controllers.ForecastIcon
import com.tragicfruit.kindweather.databinding.FragmentForecastBinding
import com.tragicfruit.kindweather.screens.WFragment
import com.tragicfruit.kindweather.utils.ColorHelper

class ForecastFragment : WFragment(), ForecastContract.View {

    private val args: ForecastFragmentArgs by navArgs()
    private val presenter = ForecastPresenter(this)

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
        presenter.init(args.forecastId, args.timeCreatedMillis, args.color)

        binding.toolbar.setNavigationOnClickListener {
            presenter.onBackClicked()
        }
    }

    override fun initView(color: Int,
                          dateString: String,
                          icon: ForecastIcon,
                          highTempString: String?,
                          lowTempString: String?,
                          precipString: String?) {

        binding.toolbar.setBackgroundColor(color)
        applyStatusBarColor(ColorHelper.darkenColor(color), lightStatusBar)

        binding.toolbar.title = dateString
        binding.mainImage.setImageResource(icon.iconRes)
        binding.highTempValueText.text = highTempString
        binding.lowTempValueText.text = lowTempString
        binding.precipValueText.text = precipString
    }

    override fun fetchAddress(latitude: Double, longitude: Double) {
        context?.let {
            FetchAddressService.start(it, latitude, longitude, object : ResultReceiver(Handler(Looper.getMainLooper())) {
                override fun onReceiveResult(resultCode: Int, resultData: Bundle?) {
                    if (isAdded) {
                        presenter.onAddressFetched(resultData?.getString(FetchAddressService.RESULT_DATA))
                    }
                }
            })
        }
    }

    override fun showAddress(address: String?) {
        binding.addressText.text = address
    }

    override fun closeScreen() {
        findNavController().navigateUp()
    }

}