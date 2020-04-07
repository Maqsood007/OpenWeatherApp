package com.maqsood007.weatherforecast.utils

import com.maqsood007.weatherforecast.data.response.currentlocation.Main
import com.maqsood007.weatherforecast.data.response.currentlocation.Wind
import java.text.DecimalFormat

/**
 * Created by Muhammad Maqsood on 07/04/2020.
 */
object CommonUtility {


    enum class TemperatureType {
        TEMPERATURE,
        MIN_TEMPERATURE,
        MAX_TEMPERATURE
    }

    fun Main.toTempString(temperatureType: TemperatureType): String {

        val CONVERTION_UNIT = 273.15

        val temperature: Double?

        when (temperatureType) {
            TemperatureType.TEMPERATURE -> temperature = temp
            TemperatureType.MIN_TEMPERATURE -> temperature = tempMin
            TemperatureType.MAX_TEMPERATURE -> temperature = tempMax
        }
        return String.format("%.0f", temperature?.minus(CONVERTION_UNIT)) + "\u2103";
    }


    fun Wind.toWindSpeed(): String {
        return String.format("%.1f", speed?.times(3.6)) + "km/h";
    }

}