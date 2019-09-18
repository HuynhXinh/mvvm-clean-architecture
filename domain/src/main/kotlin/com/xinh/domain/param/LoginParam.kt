package com.xinh.domain.param

data class LoginParam(
    var email: String? = null,
    var password: String? = null,
    var token: String? = null,
    var loginTypeParam: LoginTypeParam = LoginTypeParam.EMAIL
)

enum class LoginTypeParam {
    EMAIL, TOKEN, FACEBOOK, GOOGLE
}