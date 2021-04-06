package com.decagon.facilitymanagementapp_group_two.ms_auth

import android.content.Context
import android.content.SharedPreferences
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.decagon.facilitymanagementapp_group_two.R
import com.decagon.facilitymanagementapp_group_two.model.data.SsoResultBody
import com.decagon.facilitymanagementapp_group_two.ui.authentication.AuthorizingUserFragmentDirections
import com.decagon.facilitymanagementapp_group_two.utils.writeSsoDetailsToSharedPref
import com.microsoft.graph.concurrency.ICallback
import com.microsoft.graph.core.ClientException
import com.microsoft.graph.models.extensions.User
import com.microsoft.graph.requests.extensions.GraphServiceClient
import com.microsoft.identity.client.*
import com.microsoft.identity.client.exception.MsalException
import javax.inject.Inject

object MsWebAuthentication {
    @Inject
    lateinit var sharedPreferences: SharedPreferences
    private val TAG = "MsWebAuthentication"
    private lateinit var mSingleAccountApp: ISingleAccountPublicClientApplication
    private val scopes = arrayOf("user.read")

    // Holds the result from Microsoft SSO authentication
    lateinit var ssoResultBody: SsoResultBody

    /**
     * Call method used in signing-in users through microsoft identity platform
     */
    private fun getAuthenticationCallback(fragment: Fragment): AuthenticationCallback {
        return object : AuthenticationCallback {
            override fun onSuccess(authenticationResult: IAuthenticationResult) {
                callGraphAPI(authenticationResult, fragment)
            }

            override fun onError(exception: MsalException?) {
                logIt(exception.toString())
                logIt("Error Occurred!")
                val action = AuthorizingUserFragmentDirections.actionAuthorizingUserFragmentToFailedAuthenticationFragment()
                fragment.findNavController().navigate(action)
            }

            override fun onCancel() {
                logIt("User cancelled login.")
                fragment.findNavController().navigate(R.id.onboardingFragment)
            }
        }
    }

    /**
     * Method to call microsoft graph API
     */
    fun callGraphAPI(authenticationResult: IAuthenticationResult, fragment: Fragment) {
        val accessToken = authenticationResult.accessToken
        val graphClient = GraphServiceClient
            .builder()
            .authenticationProvider {
                logIt("Authenticating request, ${it.requestUrl}")
                it.addHeader("Authorization", "Bearer $accessToken")
            }
            .buildClient()

        graphClient
            .me()
            .buildRequest()
            .get(object : ICallback<User> {
                override fun success(result: User) {
                    /**
                     * Receives the result from microsoft authentication and saves it to sharedPreference
                     */
                    val (firstName, lastName) = result.displayName.split(" ")
                    ssoResultBody = SsoResultBody(firstName, lastName, result.mail)
                    writeSsoDetailsToSharedPref(ssoResultBody.firstName, ssoResultBody.lastName, ssoResultBody.email, sharedPreferences)
                    sharedPreferences.edit().putString("UserName", result.displayName).apply()
                    logIt(result.displayName)
                    val action = AuthorizingUserFragmentDirections
                        .actionAuthorizingUserFragmentToSuccessfulAuthFragment(result.displayName)

                    // Switch to MainThread and navigate to SuccessAuthFragment
                    Handler(Looper.getMainLooper()).post {
                        fragment.findNavController().navigate(action)
                    }
                }

                override fun failure(ex: ClientException?) {
                    logIt(ex.toString())
                }
            })
    }

    private fun logIt(message: String) {
        Log.d(TAG, message)
    }

    /**
     *  Method to initialise mSingleAccountApp
     */
    fun initialiseSingleAccount(activity: FragmentActivity, navController: NavController) {
        PublicClientApplication.createSingleAccountPublicClientApplication(
            activity.applicationContext, R.raw.auth_config_single_account,
            object : IPublicClientApplication.ISingleAccountApplicationCreatedListener {
                override fun onCreated(application: ISingleAccountPublicClientApplication) {
                    mSingleAccountApp = application
                    loadAccount(navController)
                }

                override fun onError(exception: MsalException?) {
                    logIt(exception.toString())
                }
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

                override fun onAccountChanged(priorAccount: IAccount?, currentAccount: IAccount?) {
                    logIt("Account changed!")
                }

                override fun onError(exception: MsalException) {
                    logIt(exception.toString())
                }
            }
        )
    }

    fun signInUser(activity: FragmentActivity, fragment: Fragment) {
        mSingleAccountApp.signIn(
            activity, null, scopes,
            getAuthenticationCallback(fragment)
        )
    }
}
