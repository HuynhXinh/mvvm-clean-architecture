package com.xinh.data.repository.user.disk

interface UserPreference {
    fun getUser(): String?

    fun setUser(user: String)

    fun clear()
}