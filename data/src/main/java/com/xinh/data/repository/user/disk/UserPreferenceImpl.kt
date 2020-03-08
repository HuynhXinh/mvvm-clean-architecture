package com.xinh.data.repository.user.disk

import android.content.SharedPreferences
import com.xinh.data.preference.PreferenceDelegate

class UserPreferenceImpl(prefs: SharedPreferences) :
    UserPreference {

    private var _user: String by PreferenceDelegate(
        prefs,
        ""
    )

    override fun getUser(): String? {
        return _user
    }

    override fun setUser(user: String) {
        _user = user
    }

    override fun clear() {
        _user = ""
    }
}