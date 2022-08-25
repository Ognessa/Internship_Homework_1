package com.onix.internship.network

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.util.Log

class NetworkStateManager(val context: Context) {

    //use this value wherever required
    var isOnline: Boolean

    private var callback = ConnectionStatusCallback()
    private var connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    init {
        isOnline = getInitialConnectionStatus()
        try {
            connectivityManager.unregisterNetworkCallback(callback)
        } catch (e: Exception) {
            Log.w(
                this.javaClass.name,
                "NetworkCallback for Wi-fi was not registered or already unregistered"
            )
        }
        connectivityManager.registerNetworkCallback(NetworkRequest.Builder().build(), callback)
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private lateinit var INSTANCE: NetworkStateManager

        fun getInstance(context: Context): NetworkStateManager {
            synchronized(this) {
                if (!::INSTANCE.isInitialized) {
                    INSTANCE = NetworkStateManager(context)
                }
            }
            return INSTANCE
        }
    }

    private fun getInitialConnectionStatus(): Boolean {
        val network = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(network)
        return capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) ?: false
    }

    inner class ConnectionStatusCallback : ConnectivityManager.NetworkCallback() {

        private val activeNetworks: MutableList<Network> = mutableListOf()

        override fun onLost(network: Network) {
            super.onLost(network)
            activeNetworks.removeAll { activeNetwork -> activeNetwork == network }
            isOnline = activeNetworks.isNotEmpty()
        }

        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            if (activeNetworks.none { activeNetwork -> activeNetwork == network }) {
                activeNetworks.add(network)
            }
            isOnline = activeNetworks.isNotEmpty()
        }
    }
}