package com.decagon.facilitymanagementapp_group_two.ui

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.decagon.facilitymanagementapp_group_two.R
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith

/**
 * This test class is annotated ignore because the navigation away from the app to the
 * Microsoft auth web view for authentication causes the test to malfunction
 */

@Ignore
@RunWith(AndroidJUnit4::class)
class AuthorizingUserFragmentTest {

    @Test
    fun testVisibilityOfAuthorizingUserFragmentViewsReturnTrue() {

        launchFragmentInContainer<AuthorizingUserFragment>(
            null,
            R.style.Theme_FacilityManagementAppGroupTwo
        )

        onView(withId(R.id.fragment_authorizing_user_progbar)).check(matches(isDisplayed()))
        onView(withId(R.id.fragment_authorizing_user_tv)).check(matches(isDisplayed()))
        onView(withId(R.id.fragment_authorizing_user_tv)).check(matches(withText(R.string.fragment_authorizing_user_loading)))
    }
}
