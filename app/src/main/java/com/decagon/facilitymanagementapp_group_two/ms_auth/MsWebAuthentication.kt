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
import com.decagon.facilitymanagementapp_group_two.model.data.UpdateProfileBody
import com.decagon.facilitymanagementapp_group_two.network.ApiResponseHandler
import com.decagon.facilitymanagementapp_group_two.ui.authentication.AuthorizingUserFragment
import com.decagon.facilitymanagementapp_group_two.ui.authentication.AuthorizingUserFragmentDirections
import com.decagon.facilitymanagementapp_group_two.utils.*
import com.microsoft.graph.concurrency.ICallback
import com.microsoft.graph.core.ClientException
import com.microsoft.graph.models.extensions.User
import com.microsoft.graph.requests.extensions.GraphServiceClient
import com.microsoft.identity.client.*
import com.microsoft.identity.client.exception.MsalException

object MsWebAuthentication {

    private lateinit var sharedPreferences: SharedPreferences
    private val TAG = "MsWebAuthentication"
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
                //  callGraphAPI(authenticationResult, fragment)
                val accessToken = authenticationResult.accessToken
                logIt(accessToken)
                val serverResponse = fragment.viewModel.getToken(accessToken)

                ApiResponseHandler(serverResponse, fragment, failedAction = true) {
                    sharedPreferences.edit().putString(TOKEN_NAME, it.value.data.token).apply()
                    sharedPreferences.edit().putString(USER_ID, it.value.data.id).apply()
                    Log.d("MsWebAuth", "UserId: ${it.value.data.id}")
                    // fragment.viewModel.saveAccessToken(authResponse)
                    logIt(it.toString())
                    val response = fragment.viewModel.getUserData(it.value.data.id)

                    ApiResponseHandler(response, fragment, failedAction = true) {
                        logIt(it.value.data.toString())
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
                logIt(exception.toString())
                logIt("Error Occurred!")
                val action =
                    AuthorizingUserFragmentDirections.actionAuthorizingUserFragmentToFailedAuthenticationFragment()
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
        logIt("Authenticating request, $accessToken")
        val graphClient = GraphServiceClient
            .builder()
            .authenticationProvider {
                logIt("Authenticating request, ${it.requestUrl}")
                logIt("SSO TOKEN: $accessToken")
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
                    updateProfileBody = UpdateProfileBody("SQ--", "NIL", "NIL")
//                    writeSsoDetailsToSharedPref(ssoResultBody.firstName, ssoResultBody.lastName, ssoResultBody.email,
//                        updateProfileBody.squad,
//                        updateProfileBody.stack,
//                        updateProfileBody.mobile, sharedPreferences)
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
        sharedPreferences =
            activity.applicationContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
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

            override fun onError(exception: MsalException) {
                logIt(exception.toString())
            }
        })
    }
}
