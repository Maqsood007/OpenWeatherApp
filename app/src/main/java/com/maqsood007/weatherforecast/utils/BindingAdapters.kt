package com.maqsood007.weatherforecast.utils

import android.widget.Button
import androidx.databinding.BindingAdapter

/**
 * Created by Muhammad Maqsood on 06/04/2020.
 */

@BindingAdapter("bindTextWithCityCounter")
public fun bindTextWithCityCounter(button: Button, citiesSelected: String) {
    val text = "SEARCH FORECAST ($citiesSelected)"
    button.setText(text)
}




