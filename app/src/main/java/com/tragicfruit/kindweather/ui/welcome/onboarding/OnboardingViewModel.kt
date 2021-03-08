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

    private val _imageRes: MutableLiveData<Int> = MutableLiveData()
    val imageRes: LiveData<Int> = _imageRes

    private val _titleRes: MutableLiveData<Int> = MutableLiveData()
    val titleRes: LiveData<Int> = _titleRes

    private val _descriptionRes: MutableLiveData<Int> = MutableLiveData()
    val descriptionRes: LiveData<Int> = _descriptionRes

    init {
        _imageRes.value = savedStateHandle.get<Int>(OnboardingFragment.IMAGE_RES)
        _titleRes.value = savedStateHandle.get<Int>(OnboardingFragment.TITLE_RES)
        _descriptionRes.value = savedStateHandle.get<Int>(OnboardingFragment.DESC_RES)
    }
}
