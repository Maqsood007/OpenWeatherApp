package com.maqsood007.weatherforecast.data.response.currentlocation

data class ListItem(
	val dt: Int? = null,
	val dtTxt: String? = null,
	val weather: List<WeatherItem?>? = null,
	val main: Main? = null,
	val clouds: Clouds? = null,
	val sys: Sys? = null,
	val wind: Wind? = null,
	val rain: Rain? = null
)
