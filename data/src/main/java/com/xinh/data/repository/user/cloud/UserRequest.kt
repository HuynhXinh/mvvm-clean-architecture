package com.xinh.data.repository.user.cloud

data class LoginRequest(
    var email: String,
    var password: String
)

data class LoginTokenRequest(
    var token: String
)

data class LogoutRequest(
    var token: String
)