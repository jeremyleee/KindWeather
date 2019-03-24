package com.tragicfruit.kindweather.screens.welcome.fragments.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import com.tragicfruit.kindweather.R
import com.tragicfruit.kindweather.screens.WFragment
import kotlinx.android.synthetic.main.fragment_onboarding.*

class OnboardingFragment : WFragment(), OnboardingContract.View {

    private val presenter = OnboardingPresenter(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_onboarding, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val titleRes = arguments?.getInt(TITLE_RES) ?: 0
        val descRes = arguments?.getInt(DESC_RES) ?: 0

        presenter.init(titleRes, descRes)
    }

    override fun setTitle(titleRes: Int) {
        onboardingTitle.setText(titleRes)
    }

    override fun setDescription(descRes: Int) {
        onboardingDesc.setText(descRes)
    }

    companion object {
        private const val TITLE_RES = "title-res"
        private const val DESC_RES = "desc-res"

        fun newInstance(@StringRes titleRes: Int, @StringRes descRes: Int): OnboardingFragment {
            return OnboardingFragment().also {
                it.arguments = Bundle().apply {
                    putInt(TITLE_RES, titleRes)
                    putInt(DESC_RES, descRes)
                }
            }
        }
    }

}