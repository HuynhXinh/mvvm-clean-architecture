package com.xinh.location.rxlocationmanager

import com.xinh.domain.model.MyLocation
import com.xinh.location.param.CurrentLocationParam
import io.reactivex.Maybe
import io.reactivex.Single

interface RxLocationManager {
    fun getCurrentLocation(param: CurrentLocationParam): Single<MyLocation>

    fun getLastLocation(param: CurrentLocationParam): Maybe<MyLocation>
}