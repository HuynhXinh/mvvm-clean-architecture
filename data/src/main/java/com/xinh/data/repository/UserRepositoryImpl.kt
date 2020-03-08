package com.xinh.data.repository

import com.xinh.data.extension.toEither
import com.xinh.data.mapper.UserMapper.Companion.toLoginRequest
import com.xinh.data.mapper.UserMapper.Companion.toLoginTokenRequest
import com.xinh.data.mapper.UserMapper.Companion.toUser
import com.xinh.data.repository.user.cloud.AuthenticationService
import com.xinh.data.repository.user.cloud.LogoutRequest
import com.xinh.domain.exception.Failure
import com.xinh.domain.functional.Either
import com.xinh.domain.model.User
import com.xinh.domain.repository.UserRepository
import com.xinh.facebook.service.FacebookService
import com.xinh.google.service.GoogleService
import io.reactivex.Observable

class UserRepositoryImpl(
    private val authenticationService: AuthenticationService,
    private val googleService: GoogleService,
    private val facebookService: FacebookService
) : UserRepository {
    override fun login(email: String, password: String): Observable<Either<Failure, User>> {
        return authenticationService.login(toLoginRequest(email, password))
            .toEither(::toUser)
    }

    override fun login(token: String): Observable<Either<Failure, User>> {
        return authenticationService.login(toLoginTokenRequest(token))
            .toEither(::toUser)
    }

    override fun loginFacebook(): Observable<Either<Failure, User>> {
        return facebookService.login().map { Either.Right(toUser(it)) }
    }

    override fun logoutFacebook(): Observable<Either<Failure, Boolean>> {
        return facebookService.logout()
            .map { Either.Right(true) }
    }

    override fun loginGoogle(): Observable<Either<Failure, User>> {
        return googleService.login().map { Either.Right(toUser(it)) }

    }

    override fun logout(token: String): Observable<Either<Failure, Boolean>> {
        return authenticationService.logout(
            LogoutRequest(
                token
            )
        ).map { Either.Right(true) }
    }

    override fun logoutGoogle(): Observable<Either<Failure, Boolean>> {
        return googleService.logout().map { Either.Right(true) }
    }
}
