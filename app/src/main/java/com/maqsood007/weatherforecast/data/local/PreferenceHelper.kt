package com.test.nyt_most_viewed.data.local

interface PreferenceHelper {
    fun isUserLogin(): Boolean
    fun setIsUserLogin(isLogin: Boolean)
}