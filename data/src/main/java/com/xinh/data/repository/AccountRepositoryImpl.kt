package com.xinh.data.repository

import com.xinh.domain.repository.AccountRepository
import com.xinh.facebook.service.FacebookService
import com.xinh.google.service.GoogleService
import com.xinh.remote.request.LogoutRequest
import com.xinh.remote.service.AuthenticationService
import io.reactivex.Observable

class AccountRepositoryImpl(
    private val authenticationService: AuthenticationService,
    private val googleService: GoogleService,
    private val facebookService: FacebookService
) : AccountRepository {

    override fun logoutFacebook(): Observable<Boolean> {
        return facebookService.logout()
    }

    override fun logoutByToken(token: String): Observable<Boolean> {
        return authenticationService.logout(LogoutRequest(token))
            .map { it.data }
    }

    override fun logoutGoogle(): Observable<Boolean> {
        return googleService.logout()
    }
}