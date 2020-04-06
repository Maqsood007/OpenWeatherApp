package com.maqsood007.weatherforecast.data.response.citiesforcast

data class ListItem(
	val dt: Int? = null,
	val coord: Coord? = null,
	val visibility: Int? = null,
	val weather: List<WeatherItem?>? = null,
	val name: String? = null,
	val main: Main? = null,
	val clouds: Clouds? = null,
	val id: Int? = null,
	val sys: Sys? = null,
	val wind: Wind? = null
)
