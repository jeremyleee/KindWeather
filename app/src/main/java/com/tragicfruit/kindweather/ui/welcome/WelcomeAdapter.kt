package com.tragicfruit.kindweather.ui.welcome

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.tragicfruit.kindweather.R
import com.tragicfruit.kindweather.ui.welcome.allowlocation.AllowLocationContract
import com.tragicfruit.kindweather.ui.welcome.allowlocation.AllowLocationFragment
import com.tragicfruit.kindweather.ui.welcome.onboarding.OnboardingFragment

class WelcomeAdapter(fragmentManager: FragmentManager, private val callback: AllowLocationContract.Callback)
    : FragmentPagerAdapter(fragmentManager) {

    enum class Page {
        PAGE_1,
        PAGE_2,
        PAGE_3,
        ALLOW_LOCATION
    }

    override fun getItem(position: Int): Fragment {
        return when (Page.values()[position]) {
            Page.PAGE_1 -> OnboardingFragment.newInstance(R.drawable.onboarding_1, R.string.onboarding_1_title, R.string.onboarding_1_desc)
            Page.PAGE_2 -> OnboardingFragment.newInstance(R.drawable.onboarding_2, R.string.onboarding_2_title, R.string.onboarding_2_desc)
            Page.PAGE_3 -> OnboardingFragment.newInstance(R.drawable.onboarding_3, R.string.onboarding_3_title, R.string.onboarding_3_desc)
            Page.ALLOW_LOCATION -> AllowLocationFragment.newInstance(callback)
        }
    }

    override fun getCount() = Page.values().size

}