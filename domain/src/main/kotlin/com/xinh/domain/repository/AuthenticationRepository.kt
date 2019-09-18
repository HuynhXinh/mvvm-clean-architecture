package com.xinh.domain.repository

import com.xinh.domain.model.User
import io.reactivex.Observable

interface AuthenticationRepository {
    fun login(email: String, password: String): Observable<User>

    fun login(token: String): Observable<User>

    fun loginFacebook(): Observable<User>

    fun loginGoogle(): Observable<User>

    fun logoutByToken(token: String): Observable<Boolean>

    fun logoutGoogle(): Observable<Boolean>

    fun logoutFacebook(): Observable<Boolean>
}