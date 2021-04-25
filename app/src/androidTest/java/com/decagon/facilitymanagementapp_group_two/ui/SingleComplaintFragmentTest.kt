package com.decagon.facilitymanagementapp_group_two.ui

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.decagon.facilitymanagementapp_group_two.R
import com.decagon.facilitymanagementapp_group_two.launchFragmentInHiltContainer
import com.decagon.facilitymanagementapp_group_two.ui.authentication.SuccessfulAuthFragmentArgs
import com.decagon.facilitymanagementapp_group_two.ui.others.SingleComplaintFragment
import com.decagon.facilitymanagementapp_group_two.ui.others.SingleComplaintFragmentArgs
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class SingleComplaintFragmentTest {

    @get: Rule
    var hiltRule = HiltAndroidRule(this)

    /**
     * Launches the SingleComplaintFragment in the custom HiltTestActivity
     */
    @Before
    fun testLaunchFragmentInHiltContainer() {
        val bundle = SingleComplaintFragmentArgs("1234","Food","Food tastes bad").toBundle()
        launchFragmentInHiltContainer<SingleComplaintFragment>(bundle)
    }

    @Test
    fun testSingleComplaintFragment_viewsExistsAndVisible_returnsTrue() {

        /**
         *  Espresso view matchers interact with elements in the view of the onboardFragment and
         *  checks if such elements exists and is visible
         */

        onView(withId(R.id.fragment_singleComplaint_complaintBody_tv)).check(matches(isDisplayed()))
        onView(withId(R.id.fragment_singleComplaint_complaintImage_iv)).check(matches(isDisplayed()))
        onView(withId(R.id.fragment_singleComplaint_commentCount_tv)).check(matches(withEffectiveVisibility(Visibility.INVISIBLE)))
        onView(withId(R.id.fragment_singleComplaint_commentIcon_iv)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.fragment_singleComplaint_likesCount_tv)).check(matches(isDisplayed()))
        onView(withId(R.id.fragment_singleComplaint_likesIcon_iv)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.fragment_singleComplaint_divider)).check(matches(isDisplayed()))
        onView(withId(R.id.fragment_singleComplaint_comment_tv)).check(matches(isDisplayed()))

        onView(withId(R.id.fragment_singleComplaint_complaintsRecylcerView)).check(matches(withEffectiveVisibility(Visibility.GONE)))
    }
}
