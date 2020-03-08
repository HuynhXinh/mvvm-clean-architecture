package com.xinh.data.preference

import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

@Suppress("UNCHECKED_CAST")
class PreferenceDelegate<T>(private val prefs: SharedPreferences, private val default: T) :
    ReadWriteProperty<Any?, T> {

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return when (default) {
            is String -> prefs.getString(property.name, default) as T
            is Float -> prefs.getFloat(property.name, default) as T
            is Int -> prefs.getInt(property.name, default) as T
            is Boolean -> prefs.getBoolean(property.name, default) as T
            is Long -> prefs.getLong(property.name, default) as T
            else -> default
        }
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        val editor = prefs.edit()
        when (value) {
            is String -> editor.putString(property.name, value)
            is Float -> editor.putFloat(property.name, value)
            is Int -> editor.putInt(property.name, value)
            is Boolean -> editor.putBoolean(property.name, value)
            is Long -> editor.putLong(property.name, value)
            else -> throw IllegalArgumentException(" variable type is not supported yet!!")
        }
        editor.apply()
    }
}