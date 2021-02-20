package com.tragicfruit.kindweather.ui.welcome

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.tragicfruit.kindweather.R
import com.tragicfruit.kindweather.ui.welcome.allowlocation.AllowLocationFragment
import com.tragicfruit.kindweather.ui.welcome.onboarding.OnboardingFragment

class WelcomeAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    enum class Page {
        PAGE_1,
        PAGE_2,
        PAGE_3,
        ALLOW_LOCATION
    }

    override fun getItemCount(): Int {
        return Page.values().size
    }

    override fun createFragment(position: Int): Fragment {
        return when (Page.values()[position]) {
            Page.PAGE_1 -> OnboardingFragment.newInstance(
                imageRes = R.drawable.onboarding_1,
                titleRes = R.string.onboarding_1_title,
                descRes = R.string.onboarding_1_desc
            )
            Page.PAGE_2 -> OnboardingFragment.newInstance(
                imageRes = R.drawable.onboarding_2,
                titleRes = R.string.onboarding_2_title,
                descRes = R.string.onboarding_2_desc
            )
            Page.PAGE_3 -> OnboardingFragment.newInstance(
                imageRes = R.drawable.onboarding_3,
                titleRes = R.string.onboarding_3_title,
                descRes = R.string.onboarding_3_desc
            )
            Page.ALLOW_LOCATION -> AllowLocationFragment()
        }
    }
}
