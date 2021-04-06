package com.decagon.facilitymanagementapp_group_two.ui

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.decagon.facilitymanagementapp_group_two.R
import com.decagon.facilitymanagementapp_group_two.launchFragmentInHiltContainer
import com.decagon.facilitymanagementapp_group_two.ui.authentication.SuccessfulAuthFragment
import com.decagon.facilitymanagementapp_group_two.ui.authentication.SuccessfulAuthFragmentArgs
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class SuccessfulAuthFragmentTest {
    @get: Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setUp() {
        val userName = "Godday Okoduwa"
        val bundle = SuccessfulAuthFragmentArgs(userName).toBundle()
        launchFragmentInHiltContainer<SuccessfulAuthFragment>(bundle)
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
    }

    @Test
    fun testThatTheRightContentAreDisplayedInTheViews() {
        onView(withId(R.id.fragment_successful_auth_title_tv))
            .check(matches(withText("Success!")))

        onView(withId(R.id.fragment_successful_auth_msg_tv))
            .check(matches(withText("You have been successfully\nauthorized, and logged in as\nGodday Okoduwa")))

        onView(withId(R.id.fragment_successful_auth_btn))
            .check(matches(withText(R.string.fragment_successful_auth_button)))
    }
}
