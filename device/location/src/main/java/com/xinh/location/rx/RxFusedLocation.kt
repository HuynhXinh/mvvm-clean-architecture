package com.xinh.location.rx

import com.google.android.gms.location.LocationRequest
import com.xinh.domain.model.MyLocation
import io.reactivex.Single

interface RxFusedLocation {
    companion object {
        private const val UPDATE_INTERVAL_IN_MILLISECONDS: Long = 10000
        private const val FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = UPDATE_INTERVAL_IN_MILLISECONDS

        val locationRequest = LocationRequest().apply {
            interval = UPDATE_INTERVAL_IN_MILLISECONDS
            fastestInterval = FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }

    fun getCurrentLocation(): Single<MyLocation>
}