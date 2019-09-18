package com.xinh.domain.repository

import io.reactivex.Observable

interface AccountRepository {

    fun logoutByToken(token: String): Observable<Boolean>

    fun logoutGoogle(): Observable<Boolean>

    fun logoutFacebook(): Observable<Boolean>
}