package com.decagon.facilitymanagementapp_group_two.ui

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.decagon.facilitymanagementapp_group_two.R
import com.decagon.facilitymanagementapp_group_two.launchFragmentInHiltContainer
import com.decagon.facilitymanagementapp_group_two.ui.profile.EditProfileFragment
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class EditProfileFragmentTest {
    @get: Rule
    var hiltRule = HiltAndroidRule(this)

    /**
     * Launches the DashboardFragment in the custom HiltTestActivity
     */
    @Before
    fun testLaunchFragmentInHiltContainer() {
        launchFragmentInHiltContainer<EditProfileFragment> {
        }
    }

    /**
     *  Espresso view matchers interact with elements in the view of the dashboardFragment and
     *  checks if such elements exists and is visible
     */

    @Test
    fun validate_edit_profile_layout() {
        onView(withId(R.id.edit_fragment_profile_appBarLayout)).check(matches(isDisplayed()))
        onView(withId(R.id.edit_fragment_profile_image)).check(matches(isDisplayed()))
        onView(withId(R.id.edit_fragment_profile_linear_layout)).check(matches(isDisplayed()))
    }
}
