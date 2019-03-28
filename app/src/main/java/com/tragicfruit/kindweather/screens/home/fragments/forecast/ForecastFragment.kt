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
import com.tragicfruit.kindweather.R
import com.tragicfruit.kindweather.controllers.FetchAddressService
import com.tragicfruit.kindweather.controllers.ForecastIcon
import com.tragicfruit.kindweather.screens.WFragment
import kotlinx.android.synthetic.main.fragment_forecast.*

class ForecastFragment : WFragment(), ForecastContract.View {

    override var statusBarColor = R.color.white
    private val args: ForecastFragmentArgs by navArgs()
    private val presenter = ForecastPresenter(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_forecast, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.init(args.forecastId, args.timeCreatedMillis)

        forecastToolbar.setNavigationOnClickListener {
            presenter.onBackClicked()
        }
    }

    override fun initView(dateString: String,
                          icon: ForecastIcon,
                          highTempString: String?,
                          lowTempString: String?,
                          precipString: String?) {

        forecastToolbar.title = dateString
        forecastImage.setImageResource(icon.iconRes)
        forecastHighTempValue.text = highTempString
        forecastLowTempValue.text = lowTempString
        forecastPrecipValue.text = precipString
    }

    override fun fetchAddress(latitude: Double, longitude: Double) {
        context?.let {
            FetchAddressService.start(it, latitude, longitude, object : ResultReceiver(Handler(Looper.getMainLooper())) {
                override fun onReceiveResult(resultCode: Int, resultData: Bundle?) {
                    presenter.onAddressFetched(resultData?.getString(FetchAddressService.RESULT_DATA))
                }
            })
        }
    }

    override fun showAddress(address: String?) {
        forecastAddress.text = address
    }

    override fun closeScreen() {
        findNavController().navigateUp()
    }

}