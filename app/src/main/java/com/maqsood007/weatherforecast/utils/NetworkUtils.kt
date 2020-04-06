package com.maqsood007.weatherforecast.utils

/**
 * @author Muhammad Maqsood.
 */


import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo


object NetworkUtils {


    fun isNetworkAvailable(context: Context): Boolean? {

        var isAvailable: Boolean? = false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        if (activeNetwork != null && activeNetwork.isConnected)
            isAvailable = true

        return isAvailable

    }
}