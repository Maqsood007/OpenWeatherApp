package com.maqsood007.weatherforecast.data.local

import android.content.SharedPreferences


class AppPreference(var sharedPreferences: SharedPreferences) :
    PreferenceHelper {


    override fun isUserLogin(): Boolean {
        return sharedPreferences.getBoolean(IS_USER_LOGIN, true)
    }

    override fun setIsUserLogin(isLogin: Boolean) {

        sharedPreferences.edit().apply {
            putBoolean(IS_USER_LOGIN, isLogin).apply()
        }
    }

    companion object {
        const val IS_USER_LOGIN = "is_user_logged"
    }
}