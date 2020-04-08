package com.maqsood007.weatherforecast.utils

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.maqsood007.weatherforecast.R

/**
 * Created by Muhammad Maqsood on 06/04/2020.
 */

@BindingAdapter("bindTextWithCityCounter")
public fun bindTextWithCityCounter(button: Button, citiesSelected: String) {
    val text = "SEARCH FORECAST ($citiesSelected)"
    button.setText(text)
}




