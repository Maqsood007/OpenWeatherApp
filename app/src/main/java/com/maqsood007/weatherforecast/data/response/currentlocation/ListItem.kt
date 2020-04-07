package com.maqsood007.weatherforecast.data.response.currentlocation

import com.google.gson.annotations.SerializedName

data class ListItem(
    val dt: Int? = null,
    @SerializedName("dt_txt")
    val dtTxt: String? = null,
    val weather: List<WeatherItem?>? = null,
    val main: Main? = null,
    val clouds: Clouds? = null,
    val sys: Sys? = null,
    val wind: Wind? = null,
    val rain: Rain? = null,
    val name: String? = null,
    var isExpanded: Boolean = false
)
