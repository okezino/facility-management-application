package com.decagon.facilitymanagementapp_group_two.ui

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.decagon.facilitymanagementapp_group_two.R
import org.hamcrest.CoreMatchers.not
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DashboardFragmentTest{

    /**
     * FragmentScenario attaches the fragment to the activity's root view controller and drives
     * fragment under test to the resumed state for espresso to evaluate information about the
     * UI
     *
     *  Espresso view matchers interact with elements in the view of the dashboardFragment and
     *  checks if such elements exists and is visible
     */

    @Test
    fun test_appBar_layout_visibility() {
        val scenario =
            launchFragmentInContainer<DashboardFragment>(themeResId = R.style.Theme_FacilityManagementAppGroupTwo)

        onView(withId(R.id.appBarLayout2)).check(matches(isDisplayed()))
        onView(withId(R.id.add_request)).check(matches(isDisplayed()))
        onView(withId(R.id.user_image)).check(matches(isDisplayed()))
        onView(withId(R.id.search_bar)).check(matches(withHint("Search")))
        onView(withId(R.id.request_title)).check(matches(isDisplayed()))
    }

    @Test
    fun test_dashboard_recycler_view_visibility(){
        val scenario =
            launchFragmentInContainer<DashboardFragment>(themeResId = R.style.Theme_FacilityManagementAppGroupTwo)

        onView(withId(R.id.no_complain_text)).check(matches(isDisplayed()))

    }

}