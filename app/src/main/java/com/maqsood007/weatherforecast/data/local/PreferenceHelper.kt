package com.maqsood007.weatherforecast.data.local

interface PreferenceHelper {
    fun isUserLogin(): Boolean
    fun setIsUserLogin(isLogin: Boolean)
}