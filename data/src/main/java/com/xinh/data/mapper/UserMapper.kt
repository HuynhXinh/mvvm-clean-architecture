package com.xinh.data.mapper

import com.xinh.data.repository.user.cloud.LoginRequest
import com.xinh.data.repository.user.cloud.LoginTokenRequest
import com.xinh.data.repository.user.cloud.UserDto
import com.xinh.domain.extension.safe
import com.xinh.domain.model.User
import com.xinh.domain.model.UserType
import com.xinh.facebook.model.FacebookUser
import com.xinh.google.model.GoogleUser

class UserMapper {
    companion object {
        fun toLoginRequest(email: String, password: String): LoginRequest {
            return LoginRequest(
                email,
                password
            )
        }

        fun toLoginTokenRequest(token: String): LoginTokenRequest {
            return LoginTokenRequest(token)
        }

        fun toUser(userDto: UserDto): User {
            return User(
                firstName = userDto.firstName.safe(),
                lastName = userDto.lastName.safe(),
                token = userDto.token.safe()
            )
        }

        fun toUser(googleUser: GoogleUser): User {
            return User(
                firstName = googleUser.firstName.safe(),
                lastName = googleUser.lastName.safe(),
                token = googleUser.token.safe(),
                userType = UserType.Google
            )
        }

        fun toUser(facebook: FacebookUser): User {
            return User(
                firstName = facebook.name.safe(),
                lastName = facebook.name.safe(),
                token = "",
                userType = UserType.Facebook
            )
        }
    }
}