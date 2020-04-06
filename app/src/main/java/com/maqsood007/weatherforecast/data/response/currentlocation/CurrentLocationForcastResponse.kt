package com.maqsood007.weatherforecast.data.response.currentlocation

data class CurrentLocationForcastResponse(
	val city: City? = null,
	val cnt: Int? = null,
	val cod: String? = null,
	val message: Int? = null,
	val list: List<ListItem?>? = null
)
