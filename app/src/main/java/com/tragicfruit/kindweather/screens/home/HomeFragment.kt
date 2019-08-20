package com.tragicfruit.kindweather.screens.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import com.tragicfruit.kindweather.R
import com.tragicfruit.kindweather.screens.WFragment
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : WFragment() {

    private val args: HomeFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.let {
            val navController = Navigation.findNavController(it, R.id.homeNavHostFragment)
            homeBottomNavigation.setupWithNavController(navController)

            // Show the conditions screen first if user has just onboarded
            if (args.fromOnboarding) {
                navController.navigate(R.id.action_feedFragment_to_alertListFragment)
            }
        }
    }

}
