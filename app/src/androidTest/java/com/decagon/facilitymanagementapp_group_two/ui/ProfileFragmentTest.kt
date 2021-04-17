package com.decagon.facilitymanagementapp_group_two.ui

import android.content.SharedPreferences
import androidx.room.Ignore
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.decagon.facilitymanagementapp_group_two.R
import com.decagon.facilitymanagementapp_group_two.launchFragmentInHiltContainer
import com.decagon.facilitymanagementapp_group_two.model.data.SsoResultBody
import com.decagon.facilitymanagementapp_group_two.model.data.UpdateProfileBody
import com.decagon.facilitymanagementapp_group_two.ui.profile.ProfileFragment
import com.decagon.facilitymanagementapp_group_two.utils.writeSsoDetailsToSharedPref
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class ProfileFragmentTest {
    @get: Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    /**
     * Launches the DashboardFragment in the custom HiltTestActivity
     */
    @Before
    fun testLaunchFragmentInHiltContainer() {
        hiltRule.inject()
        val ssoResultBody = SsoResultBody("Godday", "Okoduwa", "godday.okoduwa@decagon.dev")
        val updateProfileBody = UpdateProfileBody("SQ--","NIL","NIL")
        writeSsoDetailsToSharedPref(
            ssoResultBody.firstName, ssoResultBody.lastName, ssoResultBody.email,
            updateProfileBody.squad,
            updateProfileBody.stack,
            updateProfileBody.mobile,
            sharedPreferences
        )
        launchFragmentInHiltContainer<ProfileFragment> {
        }
    }

    /**
     *  Espresso view matchers interact with elements in the view of the dashboardFragment and
     *  checks if such elements exists and is visible
     */

    @Test
    fun test_profile_fragment_layout_visibility() {
        onView(withId(R.id.fragment_profile_appBarLayout)).check(matches(isDisplayed()))
        onView(withId(R.id.fragment_profile_linear_layout)).check(matches(isDisplayed()))
        onView(withId(R.id.profile_fragment_container)).check(matches(isDisplayed()))
    }
    @Test
    fun test_logout_btn_visibility() {
        onView(withId(R.id.fragment_profile_btn_logout)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
    }

    @Test
    fun test_profile_fragment_main_name_matches_incoming_sso_user_name() {
        onView(withId(R.id.fragment_profile_main_name)).check(matches(withText("Godday Okoduwa")))
    }

    @Ignore
    @Test
    fun test_profile_fragment_name_matches_incoming_sso_user_name() {
        onView(withId(R.id.fragment_profile_name)).check(matches(withText("Godday Okoduwa")))
    }
    @Test
    fun test_profile_fragment_email_matches_incoming_sso_user_mail() {
        onView(withId(R.id.fragment_profile_email)).check(matches(withText("godday.okoduwa@decagon.dev")))
    }
}
