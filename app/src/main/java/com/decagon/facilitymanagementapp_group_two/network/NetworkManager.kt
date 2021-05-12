package com.decagon.facilitymanagementapp_group_two.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.util.Log
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.decagon.facilitymanagementapp_group_two.ui.MainActivity
import com.google.android.material.snackbar.Snackbar

/**
 * Monitors the network state of the fragment passed into it and
 * notify the user when there is no active network connection
 */
class NetworkManager(val activity: FragmentActivity, val view: View) : LiveData<Boolean>() {

    private lateinit var networkCallback: ConnectivityManager.NetworkCallback
    private val cm = activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private val validNetworks: MutableSet<Network> = HashSet()

    private fun checkValidNetworks() {
        postValue(validNetworks.size > 0)
    }

    init {
        val snack = Snackbar
            .make(
                view,
                "No Internet Connection, Please check your network settings",
                Snackbar.LENGTH_INDEFINITE
            ).also { sBar ->
                sBar.setAction("OK") {
                    sBar.dismiss()
                }
            }
        this.observe(
            activity,
            Observer {
                if (it == false){
                   MainActivity.isConnected.value = false
                    snack.show()
                } else {
                    MainActivity.isConnected.value = true
                    snack.dismiss()
                }
            }
        )
    }

    override fun onActive() {
        checkValidNetworks()
        networkCallback = createNetworkCallback()
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()
        cm.registerNetworkCallback(networkRequest, networkCallback)
    }

    override fun onInactive() {
        cm.unregisterNetworkCallback(networkCallback)
    }

    private fun createNetworkCallback() = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            val networkCapabilities = cm.getNetworkCapabilities(network)
            val hasInternetCapability = networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            if (hasInternetCapability == true) {
                // check if this network actually has internet
                validNetworks.add(network)
            }
            checkValidNetworks()
        }

        override fun onLost(network: Network) {
            validNetworks.remove(network)
            checkValidNetworks()
        }
    }
}
