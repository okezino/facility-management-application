package com.decagon.facilitymanagementapp_group_two.ui

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.decagon.facilitymanagementapp_group_two.R
import com.decagon.facilitymanagementapp_group_two.launchFragmentInHiltContainer
import com.decagon.facilitymanagementapp_group_two.ui.others.SubmitFragment
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class SubmitFragmentTest {

    @get: Rule
    var hiltRule = HiltAndroidRule(this)

    /**
     * Launches the submitFragment in the custom HiltTestActivity
     */
    @Before
    fun testLaunchFragmentInHiltContainer() {
        launchFragmentInHiltContainer<SubmitFragment> {
        }
    }

    /**
     *
     *  Espresso view matchers interact with elements in the view of the SubmitFragment and
     *  checks if such elements exists and is visible
     */
    @Test
    fun test_layout_view_visibility() {
        onView(withId(R.id.feed_category_layout)).check(matches(isDisplayed()))
        onView(withId(R.id.toolbar)).check(matches(isDisplayed()))
        onView(withId(R.id.request_description_layout)).check(matches(isDisplayed()))
        onView(withId(R.id.request_subject_layout)).check(matches(isDisplayed()))
        onView(withId(R.id.select_feed_category)).check(matches(isDisplayed()))
        onView(withId(R.id.feed_category_layout)).check(matches(isDisplayed()))
        onView(withId(R.id.back_btn)).check(matches(isDisplayed()))

        onView(withId(R.id.request_subject)).perform(typeText("kitchen"), closeSoftKeyboard())
        onView(withId(R.id.request_description)).perform(typeText("kitchen food is too small"), closeSoftKeyboard())
        onView(withId(R.id.feed_category_layout)).perform(click())
    }
}
