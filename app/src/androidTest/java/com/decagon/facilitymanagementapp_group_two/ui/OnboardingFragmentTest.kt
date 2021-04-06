package com.decagon.facilitymanagementapp_group_two.ui

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.decagon.facilitymanagementapp_group_two.R
import com.decagon.facilitymanagementapp_group_two.launchFragmentInHiltContainer
import com.decagon.facilitymanagementapp_group_two.ui.authentication.OnboardingFragment
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class OnboardingFragmentTest {

    @get: Rule
    var hiltRule = HiltAndroidRule(this)

    /**
     * Launches the OnboardFragment in the custom HiltTestActivity
     */
    @Before
    fun testLaunchFragmentInHiltContainer() {
        launchFragmentInHiltContainer<OnboardingFragment> {
        }
    }

    @Test
    fun testOnboardFragment_viewsExistsAndVisible_returnsTrue() {

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
