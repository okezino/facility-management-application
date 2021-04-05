package com.decagon.facilitymanagementapp_group_two.ui

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.decagon.facilitymanagementapp_group_two.R
import com.decagon.facilitymanagementapp_group_two.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class ProfileFragmentTest {
    @get: Rule
    var hiltRule = HiltAndroidRule(this)

    /**
     * Launches the DashboardFragment in the custom HiltTestActivity
     */
    @Before
    fun testLaunchFragmentInHiltContainer() {
        launchFragmentInHiltContainer<ProfileFragment> {
        }
    }

    /**
     *  Espresso view matchers interact with elements in the view of the dashboardFragment and
     *  checks if such elements exists and is visible
     */

    @Test
    fun test_profile_fragment_layout_visibility() {
        onView(withId(R.id.fragment_profile_appBarLayout)).check(matches(isDisplayed()))
        onView(withId(R.id.fragment_profile_linear_layout)).check(matches(isDisplayed()))
        onView(withId(R.id.fragment_profile_btn_logout)).check(matches(isDisplayed()))
    }
}
