package com.xinh.remote.service

import com.xinh.remote.base.BaseResponse
import com.xinh.remote.model.UserDto
import com.xinh.remote.request.LoginRequest
import com.xinh.remote.request.LoginTokenRequest
import com.xinh.remote.request.LogoutRequest
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthenticationService {
    @POST("login")
    fun login(@Body loginRequest: LoginRequest): Observable<BaseResponse<UserDto>>

    @POST("login")
    fun loginByToken(loginRequest: LoginTokenRequest): Observable<BaseResponse<UserDto>>

    @POST("logout")
    fun logout(loginRequest: LogoutRequest): Observable<BaseResponse<Boolean>>
}