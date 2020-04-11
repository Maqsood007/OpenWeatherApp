package com.maqsood007.weatherforecast.ui.base

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.maqsood007.weatherforecast.R
import com.maqsood007.weatherforecast.WeatherApp

/**
 * Created by Muhammad Maqsood on 11/04/2020.
 */
open class BaseViewModel(val weatherApp: WeatherApp) : ViewModel() {

    val errorLayoutVisibility: MutableLiveData<Int> = MutableLiveData(View.GONE)
    val errorMessage: MutableLiveData<String> =
        MutableLiveData(weatherApp.getString(R.string.something_went_wrong))



}