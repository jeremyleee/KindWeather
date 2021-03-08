package com.tragicfruit.kindweather.ui.welcome.onboarding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val imageRes = savedStateHandle.get<Int>(OnboardingFragment.IMAGE_RES)
    val titleRes = savedStateHandle.get<Int>(OnboardingFragment.TITLE_RES)
    val descriptionRes = savedStateHandle.get<Int>(OnboardingFragment.DESC_RES)
}
