package com.xinh.travel.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.xinh.remote.util.InternetConnectionChecker

class InternetConnectionCheckerImpl(private val context: Context) :
    InternetConnectionChecker {

    override fun isNetworkAvailable() = isInternetConnected(context)
}

private fun isInternetConnected(context: Context?): Boolean {
    val cm =
        context?.applicationContext?.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager

    val activeNetwork: NetworkInfo?
    activeNetwork = cm?.activeNetworkInfo
    return activeNetwork != null && activeNetwork.isConnected
}