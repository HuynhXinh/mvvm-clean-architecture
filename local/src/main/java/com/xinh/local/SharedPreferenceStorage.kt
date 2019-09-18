package com.xinh.local

import android.content.Context
import android.content.SharedPreferences

class SharedPreferenceStorage {
    companion object {
        private const val PREFS_NAME = "TRAVEL"

        fun create(context: Context): SharedPreferences {
            return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        }
    }
}