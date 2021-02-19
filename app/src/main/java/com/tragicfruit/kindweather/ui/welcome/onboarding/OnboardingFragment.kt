package com.tragicfruit.kindweather.ui.welcome.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.os.bundleOf
import com.bumptech.glide.Glide
import com.tragicfruit.kindweather.databinding.FragmentOnboardingBinding
import com.tragicfruit.kindweather.ui.WFragment

class OnboardingFragment : WFragment(), OnboardingContract.View {

    private val presenter = OnboardingPresenter(this)

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

        val imageRes = arguments?.getInt(IMAGE_RES) ?: 0
        val titleRes = arguments?.getInt(TITLE_RES) ?: 0
        val descRes = arguments?.getInt(DESC_RES) ?: 0

        presenter.init(imageRes, titleRes, descRes)
    }

    override fun setImage(imageRes: Int) {
        Glide.with(this)
            .load(imageRes)
            .into(binding.mainImage)
    }

    override fun setTitle(titleRes: Int) {
        binding.titleText.setText(titleRes)
    }

    override fun setDescription(descRes: Int) {
        binding.descText.setText(descRes)
    }

    companion object {
        private const val IMAGE_RES = "image-res"
        private const val TITLE_RES = "title-res"
        private const val DESC_RES = "desc-res"

        fun newInstance(@DrawableRes imageRes: Int, @StringRes titleRes: Int, @StringRes descRes: Int): OnboardingFragment {
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