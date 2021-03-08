package com.tragicfruit.kindweather.ui.welcome.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.tragicfruit.kindweather.databinding.FragmentOnboardingBinding
import com.tragicfruit.kindweather.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnboardingFragment : BaseFragment() {

    private val viewModel: OnboardingViewModel by viewModels()

    private var _binding: FragmentOnboardingBinding? = null
    private val binding get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOnboardingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Glide.with(this)
            .load(viewModel.imageRes)
            .into(binding.mainImage)

        viewModel.titleRes?.let { binding.titleText.setText(it) }
        viewModel.descriptionRes?.let { binding.descriptionText.setText(it) }
    }

    companion object {
        const val IMAGE_RES = "image-res"
        const val TITLE_RES = "title-res"
        const val DESC_RES = "desc-res"

        fun newInstance(
            @DrawableRes imageRes: Int,
            @StringRes titleRes: Int,
            @StringRes descRes: Int
        ): OnboardingFragment {
            return OnboardingFragment().also {
                it.arguments = bundleOf(
                    IMAGE_RES to imageRes,
                    TITLE_RES to titleRes,
                    DESC_RES to descRes
                )
            }
        }
    }
}
