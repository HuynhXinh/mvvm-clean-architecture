package com.xinh.data.repository.user.cloud

import com.xinh.data.base.BaseResponse
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthenticationService {
    @POST("login")
    fun login(@Body loginRequest: LoginRequest): Observable<Response<BaseResponse<UserDto>>>

    @POST("login-token")
    fun login(@Body loginRequest: LoginTokenRequest): Observable<Response<BaseResponse<UserDto>>>

    @POST("logout")
    fun logout(@Body loginRequest: LogoutRequest): Observable<Response<BaseResponse<Any>>>
}