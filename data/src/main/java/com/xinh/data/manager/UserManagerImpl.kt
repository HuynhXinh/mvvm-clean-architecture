package com.xinh.data.manager

import com.google.gson.Gson
import com.xinh.domain.manager.UserManager
import com.xinh.domain.model.User
import com.xinh.local.authentication.UserPreference

class UserManagerImpl(
    private val userPreference: UserPreference,
    private val gson: Gson
) : UserManager {
    private var _user: User? = null

    override fun getUser(): User? {
        if (_user == null) {
            _user = gson.fromJson(userPreference.getUser(), User::class.java)
        }

        return _user
    }

    override fun getToken(): String? {
        return getUser()?.token
    }

    override fun saveUser(user: User) {
        userPreference.setUser(gson.toJson(user))
    }

    override fun clear() {
        _user = null
        userPreference.clear()
    }
}