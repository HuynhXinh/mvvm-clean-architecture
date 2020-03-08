package com.xinh.data.rx

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager

interface NetworkHandler {
    fun isConnected(): Boolean
}

class NetworkHandlerImpl(private val context: Context) : NetworkHandler {
    @SuppressLint("MissingPermission")
    override fun isConnected(): Boolean {
        return (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo.isConnected
    }
}