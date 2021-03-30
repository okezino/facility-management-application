package com.decagon.facilitymanagementapp_group_two.ui

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.decagon.facilitymanagementapp_group_two.R
import com.decagon.facilitymanagementapp_group_two.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.CoreMatchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class DashboardFragmentTest {

    @get: Rule
    var hiltRule = HiltAndroidRule(this)

    /**
     * Launches the DashboardFragment in the custom HiltTestActivity
     */
    @Before
    fun testLaunchFragmentInHiltContainer() {
        launchFragmentInHiltContainer<DashboardFragment> {
        }
    }

    /**
     *  Espresso view matchers interact with elements in the view of the dashboardFragment and
     *  checks if such elements exists and is visible
     */

    @Test
    fun test_appBar_layout_visibility() {
        onView(withId(R.id.appBarLayout2)).check(matches(isDisplayed()))
        onView(withId(R.id.add_request)).check(matches(isDisplayed()))
        onView(withId(R.id.user_image)).check(matches(isDisplayed()))
        onView(withId(R.id.search_bar)).check(matches(withHint("Search")))
        onView(withId(R.id.request_title)).check(matches(isDisplayed()))
    }

    @Test
    fun test_dashboard_recycler_view_visibility() {
        onView(withId(R.id.dashboard_complaint_recycler_view)).check(matches(not(isDisplayed())))
        onView(withId(R.id.no_complain_text)).check(matches(isDisplayed()))
    }
}
