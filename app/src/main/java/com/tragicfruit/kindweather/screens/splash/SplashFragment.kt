package com.tragicfruit.kindweather.screens.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.tragicfruit.kindweather.R
import com.tragicfruit.kindweather.screens.WFragment
import com.tragicfruit.kindweather.utils.SharedPrefsHelper

class SplashFragment : WFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (SharedPrefsHelper.isOnboardingCompleted()) {
            // Launch home screen
            findNavController().navigate(R.id.action_splashFragment_to_homeFragment)

        } else {
            // Launch welcome screens
            findNavController().navigate(R.id.action_splashFragment_to_welcomeFragment)
        }
    }

}