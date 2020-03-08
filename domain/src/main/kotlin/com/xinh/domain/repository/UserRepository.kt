package com.xinh.domain.repository

import com.xinh.domain.exception.Failure
import com.xinh.domain.functional.Either
import com.xinh.domain.model.User
import io.reactivex.Observable

interface UserRepository {
    fun login(email: String, password: String): Observable<Either<Failure, User>>

    fun login(token: String): Observable<Either<Failure, User>>

    fun loginFacebook(): Observable<Either<Failure, User>>

    fun loginGoogle(): Observable<Either<Failure, User>>

    fun logout(token: String): Observable<Either<Failure, Boolean>>

    fun logoutGoogle(): Observable<Either<Failure, Boolean>>

    fun logoutFacebook(): Observable<Either<Failure, Boolean>>
}