package com.tragicfruit.kindweather.ui.home.fragments.forecast

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
import com.tragicfruit.kindweather.api.ForecastIcon
import com.tragicfruit.kindweather.databinding.FragmentForecastBinding
import com.tragicfruit.kindweather.ui.WFragment
import com.tragicfruit.kindweather.utils.ColorHelper
import com.tragicfruit.kindweather.utils.SharedPrefsHelper
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ForecastFragment : WFragment(), ForecastContract.View {

    @Inject lateinit var sharedPrefsHelper: SharedPrefsHelper

    private val args: ForecastFragmentArgs by navArgs()
    private val presenter: ForecastPresenter by lazy {
        ForecastPresenter(this, sharedPrefsHelper)
    }

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