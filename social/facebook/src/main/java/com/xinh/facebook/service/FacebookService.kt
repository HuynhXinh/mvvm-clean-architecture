package com.xinh.facebook.service

import com.xinh.facebook.model.FacebookAccessToken
import com.xinh.facebook.model.FacebookUser
import io.reactivex.Observable

interface FacebookService {
    fun loginToGetAccessToken(): Observable<FacebookAccessToken>

    fun login(): Observable<FacebookUser>

    fun logout(): Observable<Boolean>
}