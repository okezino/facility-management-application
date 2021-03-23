package com.decagon.facilitymanagementapp_group_two.ui

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.decagon.facilitymanagementapp_group_two.R
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FailedAuthenticationFragmentTest {

    @Before
    fun setUp() {
        launchFragmentInContainer<FailedAuthenticationFragment>(null,
            R.style.Theme_FacilityManagementAppGroupTwo)
    }

    @Test
    fun visibilityTestOfFailedAuthenticationFragment() {
        onView(withId(R.id.fragment_failed_authentication_iv)).check(matches(isDisplayed()))
        onView(withId(R.id.fragment_failed_authentication_title_tv)).check(matches(isDisplayed()))
        onView(withId(R.id.fragment_failed_authentication_text_body_tv)).check(matches(isDisplayed()))
        onView(withId(R.id.fragment_failed_authentication_try_again_btn)).check(matches(isDisplayed()))
        onView(withId(R.id.fragment_failed_authentication_back_btn)).check(matches(isDisplayed()))
        onView(withId(R.id.fragment_failed_authentication_try_again_btn)).check(matches(isClickable()))
        onView(withId(R.id.fragment_failed_authentication_back_btn)).check(matches(isClickable()))
    }

    @Test
    fun correctContentDisplayedTest() {
        onView(withId(R.id.fragment_failed_authentication_title_tv))
            .check(matches(withText(R.string.fragment_failed_authentication_title)))

        onView(withId(R.id.fragment_failed_authentication_text_body_tv))
            .check(matches(withText(R.string.fragment_failed_authentication_text_body)))

        onView(withId(R.id.fragment_failed_authentication_try_again_btn))
            .check(matches(withText(R.string.fragment_failed_authentication_try_again_btn)))

        onView(withId(R.id.fragment_failed_authentication_back_btn))
            .check(matches(withText(R.string.fragment_failed_authentication_back_btn)))
    }
}
