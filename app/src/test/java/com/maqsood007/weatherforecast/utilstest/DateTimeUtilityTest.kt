package com.maqsood007.weatherforecast.utilstest

import com.maqsood007.weatherforecast.utils.DateTimeUtility
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*
import org.junit.runners.JUnit4

/**
 * Created by Muhammad Maqsood on 08/04/2020.
 */

@RunWith(JUnit4::class)
class DateTimeUtilityTest {


    @Test
    fun test_getDateOnly_return_true() {

        val input = "2020-04-23 14:00:00"
        val expected = "2020-04-23"

        val processedValue = DateTimeUtility.getDateOnly(input, DateTimeUtility.DATE_FORMATTER)
        assertEquals(expected,processedValue)

    }

    @Test
    fun test_getDateOnly_return_false() {

        val input = "2020-04-23 14:00:00"
        val expected = "2020-04-22"

        val processedValue = DateTimeUtility.getDateOnly(input, DateTimeUtility.DATE_FORMATTER)
        assertNotEquals(expected,processedValue)

    }


    @Test
    fun test_getDayName_return_true() {

        val input = "2020-04-23 14:00:00"
        val expected = "Thursday"

        val processedValue = DateTimeUtility.getDayName(input, DateTimeUtility.DATE_FORMATTER)
        assertEquals(expected,processedValue)

    }


    @Test
    fun test_getDayName_return_false() {

        val input = "2020-04-23 14:00:00"
        val expected = "Sunday"

        val processedValue = DateTimeUtility.getDateOnly(input, DateTimeUtility.DATE_FORMATTER)
        assertNotEquals(expected,processedValue)

    }


    @Test
    fun test_getTime_return_true() {

        val input = "2020-04-23 14:00:00"
        val expected = "02:00"

        val processedValue = DateTimeUtility.getTime(input, DateTimeUtility.DATE_FORMATTER)
        assertEquals(expected,processedValue)

    }

    @Test
    fun test_getTime_return_false() {

        val input = "2020-04-23 14:00:00"
        val expected = "14:00"

        val processedValue = DateTimeUtility.getDateOnly(input, DateTimeUtility.DATE_FORMATTER)
        // This return time in AM/PM but expected is in 24 hours format.
        assertNotEquals(expected,processedValue)


    }
}