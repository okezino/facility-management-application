package com.decagon.facilitymanagementapp_group_two.ui

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.decagon.facilitymanagementapp_group_two.R
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SingleComplaintFragmentTest {

    @Test
    fun testSingleComplaintFragment_viewsExistsAndVisible_returnsTrue() {
        /**
         * FragmentScenario attaches the fragment to the activity's root view controller and drives
         * fragment under test to the resumed state for espresso to evaluate information about the
         * UI
         */
        val scenario = launchFragmentInContainer<SingleComplaintFragment>(themeResId = R.style.Theme_FacilityManagementAppGroupOne)

        /**
         *  Espresso view matchers interact with elements in the view of the onboardFragment and
         *  checks if such elements exists and is visible
         */

        onView(withId(R.id.fragment_singleComplaint_complaintBody_tv)).check(matches(isDisplayed()))
        onView(withId(R.id.fragment_singleComplaint_complaintImage_iv)).check(matches(isDisplayed()))
        onView(withId(R.id.fragment_singleComplaint_commentCount_tv)).check(matches(isDisplayed()))
        onView(withId(R.id.fragment_singleComplaint_commentIcon_iv)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.fragment_singleComplaint_likesCount_tv)).check(matches(isDisplayed()))
        onView(withId(R.id.fragment_singleComplaint_likesIcon_iv)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.fragment_singleComplaint_divider)).check(matches(isDisplayed()))
        onView(withId(R.id.fragment_singleComplaint_comment_tv)).check(matches(isDisplayed()))

        onView(withId(R.id.fragment_singleComplaint_complaintsRecylcerView)).check(
            matches(
                isDisplayed()
            )
        )
    }
}
