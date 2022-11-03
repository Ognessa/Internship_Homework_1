package com.onix.internship.data.local

import android.content.SharedPreferences
import java.io.IOException

abstract class BasePreferences(val sharedPreferences: SharedPreferences) {

    @Throws(IOException::class)
    protected fun <T> put(key: String, value: T) {
        val editor = sharedPreferences.edit()
        when (value) {
            is Float -> editor.putFloat(key, value)
            is Int -> editor.putInt(key, value)
            is String -> editor.putString(key, value)
            is Boolean -> editor.putBoolean(key, value)
            is Long -> editor.putLong(key, value)
            is Enum<*> -> editor.putInt(key, value.ordinal)
            else -> throw IOException()
        }
        editor.apply()
    }

    @Suppress("UNCHECKED_CAST")
    @Throws(IOException::class)
    protected fun <T> get(key: String, default: T): T {
        val preferences = sharedPreferences
        return when (default) {
            is Float -> preferences.getFloat(key, default) as T
            is Int -> preferences.getInt(key, default) as T
            is String -> preferences.getString(key, default) as T
            is Boolean -> preferences.getBoolean(key, default) as T
            is Long -> preferences.getLong(key, default) as T
            else -> throw IOException()
        }
    }
}