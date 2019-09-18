package com.xinh.location.di

import android.content.Context
import android.location.LocationManager
import com.xinh.domain.repository.LocationRepository
import com.xinh.location.LocationRepositoryImpl
import com.xinh.location.rx.RxFusedLocation
import com.xinh.location.rx.RxFusedLocationImpl
import com.xinh.location.rxlocationmanager.RxLocationManager
import com.xinh.location.rxlocationmanager.RxLocationManagerImpl
import org.koin.dsl.module

val locationModule = module {

    single<LocationRepository> {
        LocationRepositoryImpl(
            context = get(),
            rxLocationManager = get(),
            rxFusedLocation = get()
        )
    }

    single { locationManagerProvider(context = get()) }

    single<RxLocationManager> { RxLocationManagerImpl(locationManager = get()) }

    single<RxFusedLocation> { RxFusedLocationImpl(context = get()) }
}

fun locationManagerProvider(context: Context): LocationManager {
    return context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
}
