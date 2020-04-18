package com.maqsood007.weatherforecast.ui.base

/**
 * @author Muhammad Maqsood.
 */


import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.maqsood007.weatherforecast.WeatherApp
import dagger.android.AndroidInjection

@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        (application as WeatherApp).getDaggerForecastComponent().inject((application as WeatherApp))
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
    }
}