package com.tragicfruit.kindweather.ui.welcome.onboarding

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val imageRes: Int? = savedStateHandle.get(OnboardingFragment.KEY_IMAGE_RES)
    val titleRes: Int? = savedStateHandle.get(OnboardingFragment.KEY_TITLE_RES)
    val descriptionRes: Int? = savedStateHandle.get(OnboardingFragment.KEY_DESC_RES)
}
