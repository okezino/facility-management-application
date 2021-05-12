package com.decagon.facilitymanagementapp_group_two.ms_auth

import android.content.Context
import android.content.SharedPreferences
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.decagon.facilitymanagementapp_group_two.R
import com.decagon.facilitymanagementapp_group_two.model.data.SsoResultBody
import com.decagon.facilitymanagementapp_group_two.model.data.UpdateProfileBody
import com.decagon.facilitymanagementapp_group_two.network.ApiResponseHandler
import com.decagon.facilitymanagementapp_group_two.ui.authentication.AuthorizingUserFragment
import com.decagon.facilitymanagementapp_group_two.ui.authentication.AuthorizingUserFragmentDirections
import com.decagon.facilitymanagementapp_group_two.utils.*
import com.microsoft.identity.client.*
import com.microsoft.identity.client.exception.MsalException

object MsWebAuthentication {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var mSingleAccountApp: ISingleAccountPublicClientApplication
    private val scopes = arrayOf("user.read")

    // Holds the result from Microsoft SSO authentication
    lateinit var ssoResultBody: SsoResultBody
    private lateinit var updateProfileBody: UpdateProfileBody

    /**
     * Call method used in signing-in users through microsoft identity platform
     */
    private fun getAuthenticationCallback(fragment: AuthorizingUserFragment): AuthenticationCallback {
        return object : AuthenticationCallback {
            override fun onSuccess(authenticationResult: IAuthenticationResult) {
                val accessToken = authenticationResult.accessToken
                val serverResponse = fragment.viewModel.getToken(accessToken)

                ApiResponseHandler(serverResponse, fragment, failedAction = true) {
                    sharedPreferences.edit().putString(TOKEN_NAME, it.value.data.token).apply()
                    sharedPreferences.edit().putString(USER_ID, it.value.data.id).apply()
                    val response = fragment.viewModel.getUserData(it.value.data.id)

                    ApiResponseHandler(response, fragment, failedAction = true) {
                        fragment.viewModel.saveUserToDatabase(it.value.data)
                        sharedPreferences.edit().putString(PROFILE_IMG_URI, it.value.data.profileImageUrl).apply()
                        val action = AuthorizingUserFragmentDirections
                            .actionAuthorizingUserFragmentToSuccessfulAuthFragment(
                                "${it.value.data.firstName}  ${it.value.data.lastName}",
                                flag = it.value.data.isProfileCompleted!!
                            )
                        fragment.findNavController().navigate(action)
                    }
                }
            }

            override fun onError(exception: MsalException?) {
                val action =
                    AuthorizingUserFragmentDirections.actionAuthorizingUserFragmentToFailedAuthenticationFragment()
                fragment.findNavController().navigate(action)
            }

            override fun onCancel() {
                fragment.findNavController().navigate(R.id.onboardingFragment)
            }
        }
    }

    /**
     *  Method to initialise mSingleAccountApp
     */
    fun initialiseSingleAccount(activity: FragmentActivity, navController: NavController) {
        sharedPreferences =
            activity.applicationContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        PublicClientApplication.createSingleAccountPublicClientApplication(
            activity.applicationContext, R.raw.auth_config_single_account,
            object : IPublicClientApplication.ISingleAccountApplicationCreatedListener {
                override fun onCreated(application: ISingleAccountPublicClientApplication) {
                    mSingleAccountApp = application
                    loadAccount(navController)
                }

                override fun onError(exception: MsalException?) {}
            }
        )
    }

    /**
     * When app comes to the foreground, load existing account to determine if user is signed in
     */
    fun loadAccount(navController: NavController) {
        val navGraph = navController.navInflater.inflate(R.navigation.nav_graph)
        mSingleAccountApp.getCurrentAccountAsync(
            object : ISingleAccountPublicClientApplication.CurrentAccountCallback {
                override fun onAccountLoaded(activeAccount: IAccount?) {
                    if (activeAccount != null) {
                        navGraph.startDestination = R.id.dashboardFragment
                        navController.graph = navGraph
                    } else {
                        navGraph.startDestination = R.id.onboardingFragment
                        navController.graph = navGraph
                    }
                }

                override fun onAccountChanged(priorAccount: IAccount?, currentAccount: IAccount?) {}

                override fun onError(exception: MsalException) {}
            }
        )
    }

    fun signInUser(activity: FragmentActivity, fragment: AuthorizingUserFragment) {
        mSingleAccountApp.signIn(
            activity, null, scopes,
            getAuthenticationCallback(fragment)
        )
    }

    fun signOutUser(fragment: Fragment? = null) {
        mSingleAccountApp.signOut(object : ISingleAccountPublicClientApplication.SignOutCallback {
            override fun onSignOut() {
                fragment?.findNavController()?.navigate(R.id.onboardingFragment)
            }

            override fun onError(exception: MsalException) {}
        })
    }
}
