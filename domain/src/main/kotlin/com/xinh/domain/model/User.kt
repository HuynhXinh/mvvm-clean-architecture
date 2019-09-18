package com.xinh.domain.model

data class User(
    var firstName: String,
    var lastName: String,
    var token: String,
    var userType: UserType = UserType.EMAIL
)

enum class UserType {
    EMAIL, GOOGLE, FACEBOOK
}