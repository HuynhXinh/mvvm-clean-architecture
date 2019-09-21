package com.xinh.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.LocationManager
import com.google.android.gms.location.LocationRequest
import com.xinh.domain.model.MyLocation
import com.xinh.domain.repository.LocationRepository
import com.xinh.location.exception.LocationSettingsNotSatisfiedException
import com.xinh.location.param.CurrentLocationParam
import com.xinh.location.param.LocationTime
import com.xinh.location.rx.RxFusedLocation
import com.xinh.location.rx.RxFusedLocation.Companion.locationRequest
import com.xinh.location.rx.SettingsFusedLocationSingleOnSubscribe
import com.xinh.location.rxlocationmanager.RxLocationManager
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class LocationRepositoryImpl(
    private val context: Context,
    private val rxLocationManager: RxLocationManager,
    private val rxFusedLocation: RxFusedLocation
) : LocationRepository {

    @SuppressLint("MissingPermission")
    override fun getCurrentLocation(): Observable<MyLocation> {
        return checkSettingsLocation(context, locationRequest).toObservable()
            .flatMap {
                if (it) {
                    startDetectCurrentLocation()
                } else {
                    throw LocationSettingsNotSatisfiedException()
                }
            }
    }

    private fun startDetectCurrentLocation(): Observable<MyLocation> {
        return Observable.mergeDelayError(getLocationFromLocationManager(), getFusedLocation())
    }

    private fun checkSettingsLocation(
        context: Context,
        locationRequest: LocationRequest
    ): Single<Boolean> {
        return Single.create(SettingsFusedLocationSingleOnSubscribe(context, locationRequest))
    }

    private fun getLocationFromLocationManager(): Observable<MyLocation> {
        val param =
            CurrentLocationParam(LocationManager.NETWORK_PROVIDER, LocationTime(1, TimeUnit.HOURS))

        return Observable.mergeDelayError(
            rxLocationManager.getCurrentLocation(param)
                .subscribeOn(Schedulers.computation())
                .toObservable(),

            rxLocationManager.getLastLocation(param)
                .subscribeOn(Schedulers.computation())
                .toObservable()
        )
    }

    private fun getFusedLocation(): Observable<MyLocation> {
        return rxFusedLocation.getCurrentLocation()
            .subscribeOn(Schedulers.computation())
            .toObservable()
    }
}