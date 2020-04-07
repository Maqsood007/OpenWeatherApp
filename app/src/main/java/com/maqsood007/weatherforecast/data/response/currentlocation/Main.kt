package com.maqsood007.weatherforecast.data.response.currentlocation

import com.google.gson.annotations.SerializedName

data class Main(
	val temp: Double? = null,
	@SerializedName("temp_min")
	val tempMin: Double? = null,
	val grndLevel: Int? = null,
	val tempKf: Double? = null,
	val humidity: Int? = null,
	val pressure: Int? = null,
	val seaLevel: Int? = null,
	val feelsLike: Double? = null,
	@SerializedName("temp_max")
	val tempMax: Double? = null
)
