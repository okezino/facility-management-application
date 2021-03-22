package com.decagon.facilitymanagementapp_group_two.ms_auth

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.core.os.HandlerCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.fragment.findNavController
import com.decagon.facilitymanagementapp_group_two.R
import com.decagon.facilitymanagementapp_group_two.ui.OnboardingFragment
import com.decagon.facilitymanagementapp_group_two.ui.OnboardingFragmentDirections
import com.microsoft.graph.concurrency.ICallback
import com.microsoft.graph.core.ClientException
import com.microsoft.graph.models.extensions.User
import com.microsoft.graph.requests.extensions.GraphServiceClient
import com.microsoft.identity.client.*
import com.microsoft.identity.client.exception.MsalException

object MsWebAuthentication {
    private val TAG = "MsWebAuthentication"
    private lateinit var mSingleAccountApp: ISingleAccountPublicClientApplication
    private val scopes = arrayOf("user.read")
    lateinit var user: String

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
            }

            override fun onCancel() {
                logIt("User cancelled login.")
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
                    user = result.displayName
                    logIt(user)
                    val action = OnboardingFragmentDirections
                        .actionOnboardingFragmentToSuccessfulAuthFragment(user)

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
    fun initialiseSingleAccount(fragment: Fragment) {
        PublicClientApplication.createSingleAccountPublicClientApplication(
            fragment.requireContext().applicationContext, R.raw.auth_config_single_account,
            object : IPublicClientApplication.ISingleAccountApplicationCreatedListener {
                override fun onCreated(application: ISingleAccountPublicClientApplication) {
                    mSingleAccountApp = application
                    loadAccount(fragment)
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
    fun loadAccount(fragment: Fragment) {
        mSingleAccountApp.getCurrentAccountAsync(
            object : ISingleAccountPublicClientApplication.CurrentAccountCallback {
                override fun onAccountLoaded(activeAccount: IAccount?) {
                    if (activeAccount != null) {
                        val action = OnboardingFragmentDirections
                            .actionOnboardingFragmentToSuccessfulAuthFragment("Waheed Afolabi")
                        fragment.findNavController().navigate(action)
                    } else {
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
        mSingleAccountApp.signIn(activity, null, scopes,
                getAuthenticationCallback(fragment))
    }
}
