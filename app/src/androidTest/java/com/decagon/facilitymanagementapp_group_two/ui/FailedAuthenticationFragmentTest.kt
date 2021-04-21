package com.decagon.facilitymanagementapp_group_two.ui

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.decagon.facilitymanagementapp_group_two.R
import com.decagon.facilitymanagementapp_group_two.launchFragmentInHiltContainer
import com.decagon.facilitymanagementapp_group_two.ui.authentication.FailedAuthenticationFragment
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test

@Ignore
@HiltAndroidTest
class FailedAuthenticationFragmentTest {

    @get: Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun testLaunchFragmentInHiltContainer() {
        launchFragmentInHiltContainer<FailedAuthenticationFragment> {
        }
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
        onView(withId(R.id.fragment_failed_authentication_back_navigation)).check(matches(isClickable()))
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
