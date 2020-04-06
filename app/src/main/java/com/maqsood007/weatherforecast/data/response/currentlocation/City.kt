package com.maqsood007.weatherforecast.data.response.currentlocation

data class City(
	val country: String? = null,
	val coord: Coord? = null,
	val sunrise: Int? = null,
	val timezone: Int? = null,
	val sunset: Int? = null,
	val name: String? = null,
	val id: Int? = null,
	val population: Int? = null
)
