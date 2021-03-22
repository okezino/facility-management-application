package com.decagon.facilitymanagementapp_group_two.ui

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.decagon.facilitymanagementapp_group_two.R
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class   OnboardingFragmentTest {

    @Test
    fun testOnboardFragment_viewsExistsAndVisible_returnsTrue() {
        /**
         * FragmentScenario attaches the fragment to an activity's root view controller and drives
         * fragment under test to the resumed state for espresso to evaluate information about the
         * UI
         */
        val scenario = launchFragmentInContainer<OnboardingFragment>()

        /**
         *  Espresso view matchers interact with elements in the view of the onboardFragment and
         *  checks if such elements exists and is visible
         */
        onView(withId(R.id.fragment_onboard_facilityManagementApp_tv)).check(matches(isDisplayed()))
        onView(withId(R.id.fragment_onboard_businessUsers_iv)).check(matches(isDisplayed()))
        onView(withId(R.id.fragment_onboard_manageComplaint_tv)).check(matches(isDisplayed()))
        onView(withId(R.id.fragment_onboard_getStarted_btn)).check(matches(isDisplayed()))
    }
}
