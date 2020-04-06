package com.maqsood007.weatherforecast.data.response.cities

data class City(
    val country: String? = null,
    val coord: Coord? = null,
    val name: String? = null,
    val id: Int? = null,
    val state: String? = null,
    var isSelected: Boolean = false
)
