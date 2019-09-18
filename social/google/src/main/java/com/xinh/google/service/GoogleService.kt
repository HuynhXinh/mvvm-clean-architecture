package com.xinh.google.service

import com.xinh.google.model.GoogleUser
import io.reactivex.Observable
import io.reactivex.Single

interface GoogleService {
    fun login(): Observable<GoogleUser>

    fun logout(): Observable<Boolean>
}