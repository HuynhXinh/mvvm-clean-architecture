package com.xinh.data.repository

import com.xinh.data.mapper.AuthenticationMapper.Companion.toLoginRequest
import com.xinh.data.mapper.AuthenticationMapper.Companion.toLoginTokenRequest
import com.xinh.data.mapper.AuthenticationMapper.Companion.toUser
import com.xinh.domain.error.ResponseNullPointerException
import com.xinh.domain.model.User
import com.xinh.domain.repository.AuthenticationRepository
import com.xinh.facebook.service.FacebookService
import com.xinh.google.service.GoogleService
import com.xinh.remote.request.LogoutRequest
import com.xinh.remote.service.AuthenticationService
import io.reactivex.Observable

class AuthenticationRepositoryImpl(
    private val authenticationService: AuthenticationService,
    private val googleService: GoogleService,
    private val facebookService: FacebookService
) : AuthenticationRepository {
    override fun login(email: String, password: String): Observable<User> {
        return authenticationService.login(toLoginRequest(email, password))
            .map {
                val userDto = it.data ?: throw ResponseNullPointerException()
                toUser(userDto)
            }
    }

    override fun login(token: String): Observable<User> {
        return authenticationService.loginByToken(toLoginTokenRequest(token))
            .map {
                val userDto = it.data ?: throw ResponseNullPointerException()
                toUser(userDto)
            }
    }

    override fun loginFacebook(): Observable<User> {
        return facebookService.loginToGetUser().map { toUser(it) }
    }

    override fun logoutFacebook(): Observable<Boolean> {
        return facebookService.logout()
    }

    override fun loginGoogle(): Observable<User> {
        return googleService.login().map { toUser(it) }

    }

    override fun logoutByToken(token: String): Observable<Boolean> {
        return authenticationService.logout(LogoutRequest(token))
            .map { it.data }
    }

    override fun logoutGoogle(): Observable<Boolean> {
        return googleService.logout()
    }
}