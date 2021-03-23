package com.decagon.facilitymanagementapp_group_two.ui

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.decagon.facilitymanagementapp_group_two.R
import org.hamcrest.Matchers.not
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FeedsFragmentTest{

    /**
     * FragmentScenario attaches the fragment to the activity's root view controller and drives
     * fragment under test to the resumed state for espresso to evaluate information about the
     * UI
     *
     *  Espresso view matchers interact with elements in the view of the dashboardFragment and
     *  checks if such elements exists and is visible
     */

    @Test
    fun test_appBar_layout_visibility(){
        val scenario =
                launchFragmentInContainer<FeedsFragment>(themeResId = R.style.Theme_FacilityManagementAppGroupTwo)

        onView(withId(R.id.appBarLayout)).check(matches(isDisplayed()))
        onView(withId(R.id.feedText)).check(matches(isDisplayed()))
        onView(withId(R.id.TabLay)).check(matches(isDisplayed()))
        onView(withId(R.id.viewPager)).check(matches(isDisplayed()))
    }




}