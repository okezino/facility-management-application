package com.decagon.facilitymanagementapp_group_two.ui

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.decagon.facilitymanagementapp_group_two.R
import org.junit.After
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith

@Ignore
@RunWith(AndroidJUnit4::class)
class AuthorizingUserFragmentTest {

    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setUp() {
        scenario = ActivityScenario.launch(MainActivity::class.java)
        scenario.moveToState(Lifecycle.State.STARTED)
    }

    @After
    fun tearDown() {
        scenario.moveToState(Lifecycle.State.DESTROYED)
    }

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
