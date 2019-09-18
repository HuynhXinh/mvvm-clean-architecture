package com.xinh.location.rxlocationmanager

import android.Manifest
import android.annotation.SuppressLint
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import androidx.annotation.RequiresPermission
import com.xinh.domain.model.MyLocation
import com.xinh.location.exception.ElderLocationException
import com.xinh.location.exception.ProviderDisabledException
import com.xinh.location.exception.ProviderHasNoLastLocationException
import com.xinh.location.param.CurrentLocationParam
import com.xinh.location.param.LocationTime
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.SingleOnSubscribe

class RxLocationManagerImpl(private val locationManager: LocationManager) : RxLocationManager {

    @SuppressLint("MissingPermission")
    @RequiresPermission(anyOf = [Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION])
    override fun getLastLocation(param: CurrentLocationParam): Maybe<MyLocation> {
        val provider = param.provider
        val timeOut = param.timeOut

        return Maybe.fromCallable { getLastKnownLocation(provider, timeOut) }
    }

    @RequiresPermission(anyOf = [Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION])
    private fun getLastKnownLocation(
        provider: String,
        timeOut: LocationTime?
    ): MyLocation {
        var lastKnownLocation: Location? = null

        try {
            lastKnownLocation = locationManager.getLastKnownLocation(provider)
        } catch (ex: Exception) {
        }

        if (lastKnownLocation == null) throw ProviderHasNoLastLocationException(provider)

        if (timeOut != null && !lastKnownLocation.isNotOld(timeOut)) throw ElderLocationException(
            lastKnownLocation
        )

        return MyLocation(
            lastKnownLocation.latitude,
            lastKnownLocation.longitude
        )
    }

    @SuppressLint("MissingPermission")
    @RequiresPermission(anyOf = [Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION])
    override fun getCurrentLocation(param: CurrentLocationParam): Single<MyLocation> {
        val provider = param.provider
        val timeOut = param.timeOut

        return Single.create(SingleOnSubscribe<Location> {
            if (locationManager.isProviderEnabled(provider)) {

                val locationListener = object : LocationListener {
                    override fun onLocationChanged(location: Location) {
                        if (!it.isDisposed) {
                            it.onSuccess(location)
                        }
                    }

                    override fun onProviderDisabled(p: String?) {
                        if (provider == p && !it.isDisposed) {
                            it.onError(ProviderDisabledException(provider))
                        }
                    }

                    override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {}

                    override fun onProviderEnabled(p: String?) {}
                }

                locationManager.requestSingleUpdate(
                    provider,
                    locationListener,
                    Looper.getMainLooper()
                )

                if (!it.isDisposed) {
                    it.setCancellable { locationManager.removeUpdates(locationListener) }
                }

            } else {
                if (!it.isDisposed) {
                    it.onError(ProviderDisabledException(provider))
                }
            }
        })
            .compose { if (timeOut != null) it.timeout(timeOut.time, timeOut.timeUnit) else it }
            .map { MyLocation(it.latitude, it.longitude) }
    }
}

fun Location.isNotOld(howOldCanBe: LocationTime): Boolean {
    return System.currentTimeMillis() - time < howOldCanBe.timeUnit.toMillis(howOldCanBe.time)
}
