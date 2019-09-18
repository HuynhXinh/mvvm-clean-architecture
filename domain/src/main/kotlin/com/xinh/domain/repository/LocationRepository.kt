package com.xinh.domain.repository

import com.xinh.domain.model.MyLocation
import io.reactivex.Observable

interface LocationRepository {
    fun getCurrentLocation(): Observable<MyLocation>
}