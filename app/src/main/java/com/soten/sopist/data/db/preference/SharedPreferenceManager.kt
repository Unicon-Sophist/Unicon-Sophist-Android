package com.soten.sopist.data.db.preference

import android.content.SharedPreferences
import androidx.core.content.edit
import javax.inject.Inject

class SharedPreferenceManager @Inject constructor(
    private val sharedPreferences: SharedPreferences,
) {

    fun getString(key: String): String =
        sharedPreferences.getString(key, KEY_DEFAULT) ?: KEY_DEFAULT

    fun putString(key: String, value: String) =
        sharedPreferences.edit { putString(key, value) }

    fun getBoolean(key: String) =
        sharedPreferences.getBoolean(key, false)

    fun putBoolean(key: String, value: Boolean) =
        sharedPreferences.edit { putBoolean(key, value) }

    companion object {
        const val KEY_DEFAULT = "KEY_DEFAULT"
        const val KEY_LOGIN = "IS_LOGIN"

        const val KEY_USER_NAME = "KEY_USER_NAME"
    }

}