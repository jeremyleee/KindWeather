package com.tragicfruit.kindweather.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import com.tragicfruit.kindweather.R
import com.tragicfruit.kindweather.databinding.FragmentHomeBinding
import com.tragicfruit.kindweather.ui.WFragment

class HomeFragment : WFragment() {

    private val args: HomeFragmentArgs by navArgs()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.let {
            val navController = Navigation.findNavController(it, R.id.nav_host_fragment)
            binding.bottomNav.setupWithNavController(navController)

            // Show the conditions screen first if user has just onboarded
            if (args.fromOnboarding) {
                navController.navigate(R.id.action_feedFragment_to_alertListFragment)
            }
        }
    }

}
