package com.decagon.facilitymanagementapp_group_two.ui

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.decagon.facilitymanagementapp_group_two.R
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.core.Is
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SubmitFragmentTest{


    /**
     * FragmentScenario attaches the fragment to the activity's root view controller and drives
     * fragment under test to the resumed state for espresso to evaluate information about the
     * UI
     *
     *  Espresso view matchers interact with elements in the view of the SubmitFragment and
     *  checks if such elements exists and is visible
     */
    @Test
    fun test_layout_view_visibility(){
        val scenario =
            launchFragmentInContainer<SubmitFragment>(themeResId = R.style.Theme_FacilityManagementAppGroupTwo)
        onView(withId(R.id.feed_category_layout)).check(matches(isDisplayed()))
        onView(withId(R.id.toolbar)).check(matches(isDisplayed()))
        onView(withId(R.id.request_description_layout)).check(matches(isDisplayed()))
        onView(withId(R.id.request_subject_layout)).check(matches(isDisplayed()))
        onView(withId(R.id.select_feed_category)).check(matches(isDisplayed()))
        onView(withId(R.id.feed_category_layout)).check(matches(isDisplayed()))
        onView(withId(R.id.back_btn)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_submit)).check(matches(isDisplayed()))


        onView(withId(R.id.request_subject)).perform(typeText("kitchen"), closeSoftKeyboard())
        onView(withId(R.id.request_description)).perform(typeText("kitchen food is too small"), closeSoftKeyboard())
        onView(withId(R.id.feed_category_layout)).perform(click())




    }

}