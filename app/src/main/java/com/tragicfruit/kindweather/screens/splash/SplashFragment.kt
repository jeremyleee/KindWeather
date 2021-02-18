package com.tragicfruit.kindweather.screens.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.tragicfruit.kindweather.R
import com.tragicfruit.kindweather.databinding.FragmentSplashBinding
import com.tragicfruit.kindweather.screens.WFragment
import com.tragicfruit.kindweather.utils.SharedPrefsHelper

class SplashFragment : WFragment() {

    private var _binding: FragmentSplashBinding? = null
    private val binding get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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