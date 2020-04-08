package com.maqsood007.weatherforecast.utilstest

import com.maqsood007.weatherforecast.data.response.currentlocation.Main
import com.maqsood007.weatherforecast.data.response.currentlocation.Wind
import com.maqsood007.weatherforecast.utils.CommonUtility
import com.maqsood007.weatherforecast.utils.CommonUtility.convertToTitleCaseIteratingChars
import com.maqsood007.weatherforecast.utils.CommonUtility.toTempString
import com.maqsood007.weatherforecast.utils.CommonUtility.toWindSpeed
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import org.mockito.Mockito.mock

/**
 * Created by Muhammad Maqsood on 08/04/2020.
 */
@RunWith(JUnit4::class)
class CommonUtilityTest {


    @Test
    fun test_capitalize_string_true() {

        val input = "this is weather app"
        val expectation = "This Is Weather App"

        val capitalizedString = input.convertToTitleCaseIteratingChars()

        assertTrue(capitalizedString.equals(expectation))

    }


    @Test
    fun test_capitalize_string_false() {

        val input = ""
        val expectation = "This Is Weather App"

        val capitalizedString = input.convertToTitleCaseIteratingChars()

        assertFalse(capitalizedString.equals(expectation))

    }


    @Test
    fun test_toString_temperature_true() {

        val tempInKelvin = 300.0
        val tempInCelsius = "27℃"

        val main: Main = mock(Main::class.java)
        Mockito.`when`(main.temp).thenReturn(tempInKelvin)

        val formattedTemp = main.toTempString(CommonUtility.TemperatureType.TEMPERATURE)

        assertEquals(tempInCelsius, formattedTemp)
    }

    @Test
    fun test_toString_min_temperature_true() {

        val tempInKelvin = 300.0
        val tempInCelsius = "27℃"

        val main: Main = mock(Main::class.java)
        Mockito.`when`(main.tempMin).thenReturn(tempInKelvin)

        val formattedTemp = main.toTempString(CommonUtility.TemperatureType.MIN_TEMPERATURE)

        assertEquals(tempInCelsius, formattedTemp)

    }

    @Test
    fun test_toString_max_temperature_true() {

        val tempInKelvin = 300.0
        val tempInCelsius = "27℃"

        val main: Main = mock(Main::class.java)
        Mockito.`when`(main.tempMax).thenReturn(tempInKelvin)

        val formattedTemp = main.toTempString(CommonUtility.TemperatureType.MAX_TEMPERATURE)

        assertEquals(tempInCelsius, formattedTemp)
    }


    @Test
    fun test_toString_winds() {

        val expectation = "86km/h"

        val wind: Wind = mock(Wind::class.java)
        Mockito.`when`(wind.speed).thenReturn(23.987675)

        val formattedWind = wind.toWindSpeed()

        assertEquals(expectation,formattedWind)
    }

}