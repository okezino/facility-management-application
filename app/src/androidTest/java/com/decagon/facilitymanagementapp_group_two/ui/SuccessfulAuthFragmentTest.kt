package com.decagon.facilitymanagementapp_group_two.ui

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.decagon.facilitymanagementapp_group_two.R
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SuccessfulAuthFragmentTest {

    @Before
    fun setUp() {
        val userName = "Godday Okoduwa"
        val bundle = SuccessfulAuthFragmentArgs(userName).toBundle()
        launchFragmentInContainer<SuccessfulAuthFragment>(bundle, R.style.Theme_FacilityManagementAppGroupTwo)
    }

    @Test
    fun testVisibilityOfSuccessfulAuthFragmentViews() {
        onView(withId(R.id.fragment_successful_auth_ll)).check(matches(isDisplayed()))
        onView(withId(R.id.fragment_successful_auth_ll2)).check(matches(isDisplayed()))
        onView(withId(R.id.fragment_successful_auth_v)).check(matches(isDisplayed()))
        onView(withId(R.id.fragment_successful_auth_title_tv)).check(matches(isDisplayed()))
        onView(withId(R.id.fragment_successful_auth_msg_tv)).check(matches(isDisplayed()))
        onView(withId(R.id.fragment_successful_auth_btn)).check(matches(isDisplayed()))
        onView(withId(R.id.fragment_successful_auth_btn)).check(matches(isClickable()))
        onView(withId(R.id.fragment_successful_auth_btn)).perform(click())
    }

    @Test
    fun testThatTheRightContentAreDisplayedInTheViews() {
        onView(withId(R.id.fragment_successful_auth_title_tv))
            .check(matches(withText("Success!")))

        onView(withId(R.id.fragment_successful_auth_msg_tv))
            .check(matches(withText("You have been successfully authorized, and logged in as Godday Okoduwa")))

        onView(withId(R.id.fragment_successful_auth_btn))
            .check(matches(withText(R.string.fragment_successful_auth_button)))
    }
}
