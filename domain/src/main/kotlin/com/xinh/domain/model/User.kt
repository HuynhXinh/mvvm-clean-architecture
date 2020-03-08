package com.xinh.domain.model

data class User(
        var firstName: String,
        var lastName: String,
        var token: String,
        var userType: UserType = UserType.Email
)

sealed class UserType {
    object Email : UserType()
    object Facebook : UserType()
    object Google : UserType()
}