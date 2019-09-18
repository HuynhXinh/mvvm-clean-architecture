package com.xinh.local.authentication

interface UserPreference {
    fun getUser(): String?

    fun setUser(user: String)

    fun clear()
}