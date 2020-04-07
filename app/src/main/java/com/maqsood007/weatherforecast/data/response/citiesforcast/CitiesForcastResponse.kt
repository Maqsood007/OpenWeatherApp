package com.maqsood007.weatherforecast.data.response.citiesforcast

import com.maqsood007.weatherforecast.data.response.currentlocation.ListItem

data class CitiesForcastResponse(
	val cnt: Int? = null,
	val list: List<ListItem?>? = null
)
