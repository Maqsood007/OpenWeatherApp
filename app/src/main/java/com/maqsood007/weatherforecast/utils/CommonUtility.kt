package com.maqsood007.weatherforecast.utils

import com.maqsood007.weatherforecast.BuildConfig
import com.maqsood007.weatherforecast.data.response.currentlocation.Main
import com.maqsood007.weatherforecast.data.response.currentlocation.Wind

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
        return String.format("%.0f", speed?.times(3.6)) + "km/h";
    }

    fun String.toImageUrl(): String {
        return "${BuildConfig.BASE_URL_ICON}${this}.png"
    }

    fun String.convertToTitleCaseIteratingChars(): String? {

        val text = this

        if (text.isEmpty()) {
            return text
        }
        val converted = StringBuilder()
        var convertNext = true
        for (ch in text.toCharArray()) {
            var char = ch
            if (Character.isSpaceChar(char)) {
                convertNext = true
            } else if (convertNext) {
                char = Character.toTitleCase(char)
                convertNext = false
            } else {
                char = Character.toLowerCase(char)
            }
            converted.append(char)
        }
        return converted.toString()
    }
}