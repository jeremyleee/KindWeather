package com.tragicfruit.kindweather.ui.welcome.onboarding

import androidx.lifecycle.SavedStateHandle
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class OnboardingViewModelTest {

    private lateinit var viewModel: OnboardingViewModel

    @Before
    fun setUp() {
        val savedStateHandle = SavedStateHandle(
            mapOf(
                OnboardingFragment.KEY_IMAGE_RES to 123,
                OnboardingFragment.KEY_TITLE_RES to 456,
                OnboardingFragment.KEY_DESC_RES to 789
            )
        )
        viewModel = OnboardingViewModel(savedStateHandle)
    }

    @Test
    fun testGetViewResources() {
        assertThat(viewModel.imageRes).isEqualTo(123)
        assertThat(viewModel.titleRes).isEqualTo(456)
        assertThat(viewModel.descriptionRes).isEqualTo(789)
    }
}
