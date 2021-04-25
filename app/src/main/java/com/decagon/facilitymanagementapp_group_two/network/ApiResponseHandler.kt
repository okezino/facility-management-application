package com.decagon.facilitymanagementapp_group_two.network

import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.decagon.facilitymanagementapp_group_two.R
import com.decagon.facilitymanagementapp_group_two.utils.showSnackBar

/**
 * A generic class for handling network call responses with common HTTP error codes.
 * if the response from the network call is an error message, it is displayed to the users
 * with the appropriate messages using snack bar. If the response is successful, corresponding
 * actions are executed.
 */

data class ApiResponseHandler<T>(
    val resultStatus: LiveData<ResultStatus<T>>,
    val fragment: Fragment,
    var view: View? = null,
    var failedAction: Boolean = false,
    var action: ((result: ResultStatus.Success<T>) -> Unit)? = null
) {

    init {
        handleNetworkResponse()
    }

    private fun handleNetworkResponse() {
        resultStatus.observe(
            fragment,
            Observer {
                val message: String
                when (it) {
                    is ResultStatus.Loading -> view?.showSnackBar(it.message)
                    is ResultStatus.NetworkError -> {
                        if (failedAction) fragment.findNavController().navigate(R.id.failedAuthenticationFragment)
                        view?.showSnackBar("No internet connection, please check your network settings and try again")
                    }
                    is ResultStatus.GenericError -> {
                        Log.d("ApiCall Error", "${it.code}")
                        if (failedAction) fragment.findNavController().navigate(R.id.failedAuthenticationFragment)
                        message = when (it.code) {
                            400 -> "Bad request! Please verify your inputs"
                            401 -> "Your session has expired. Please logout and login again to continue"
                            403 -> "Access to that resource is forbidden."
                            404 -> "The requested resource was not found."
                            405 -> "Method not allowed. Try again"
                            406 -> "Not acceptable response. Please verify your request"
                            407 -> "Proxy Authentication Required."
                            408 -> "Request timeout. Please try again"
                            409 -> "Could not process request"
                            410 -> "The requested resource is gone and won’t be coming back."
                            412 -> "Precondition Failed. Please try again"
                            413 -> "Payload Too Large. Please resize your request"
                            414 -> "URI Too Long"
                            415 -> "Unsupported Media Type"
                            416 -> "Range Not Satisfiable"
                            417 -> "Expectation Failed. Try again"
                            in 422..431 -> "The request could not be understood by the server due to wrong request format"
                            451 -> "Unavailable for Legal Reasons"
                            500 -> "There was an error on the server and the request could not be completed"
                            501 -> "Not Implemented."
                            502 -> "Bad Gateway."
                            503 -> "The server is unavailable to handle this request right now."
                            505 -> "HTTP Version Not Supported."
                            511 -> "Network Authentication Required."
                            521 -> "Web server is down."
                            525 -> "Unable to establish a secure connection"
                            else -> "Unknown error occurred while processing request. Please try again"
                        }
                        view?.showSnackBar("Error: $message")
                    }
                    is ResultStatus.Success -> action?.invoke(it)
                }
            }
        )
    }
}
