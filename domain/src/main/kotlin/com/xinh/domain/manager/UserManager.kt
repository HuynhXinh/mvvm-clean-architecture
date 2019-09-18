package com.xinh.domain.manager

import com.xinh.domain.model.User

interface UserManager {
    fun getUser(): User?

    fun getToken(): String?

    fun saveUser(user: User)

    fun clear()
}