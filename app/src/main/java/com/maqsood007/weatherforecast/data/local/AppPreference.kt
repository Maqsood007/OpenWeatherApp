package com.test.nyt_most_viewed.data.local

import android.content.Context
import android.content.SharedPreferences
import android.database.Observable
import android.preference.PreferenceManager
import androidx.databinding.ObservableBoolean
import javax.inject.Inject


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