package com.maqsood007.weatherforecast.utils

import android.widget.Button
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.maqsood007.weatherforecast.extensions.formatErrorLayout

/**
 * Created by Muhammad Maqsood on 06/04/2020.
 */

@BindingAdapter("bindTextWithCityCounter")
public fun bindTextWithCityCounter(button: Button, citiesSelected: String) {
    val text = "SEARCH FORECAST ($citiesSelected)"
    button.setText(text)
}

@BindingAdapter("bindErrorLayoutText")
public fun bindErrorLayoutText(textView: TextView, text: String?) {
    textView.formatErrorLayout(text)
}



