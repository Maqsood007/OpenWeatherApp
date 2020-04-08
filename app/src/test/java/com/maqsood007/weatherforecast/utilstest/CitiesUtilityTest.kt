package com.maqsood007.weatherforecast.utilstest

import com.maqsood007.weatherforecast.data.response.cities.City
import com.maqsood007.weatherforecast.data.response.currentlocation.Wind
import com.maqsood007.weatherforecast.utils.CitiesUtility
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.lang.reflect.Field

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */


@RunWith(JUnit4::class)
class CitiesUtilityTest {

    lateinit var privateCitiesField: Field
    lateinit var citiesUtility: CitiesUtility

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        citiesUtility  = Mockito.mock(CitiesUtility::class.java)
        privateCitiesField = CitiesUtility::class.java.getDeclaredField("cities")
        privateCitiesField.setAccessible(true)
    }




    @Test
    fun test_get_cities_return_null() {
        privateCitiesField.set("cities", null)
        val citiesList = privateCitiesField.get(citiesUtility)
        assertNull(citiesList)
    }

    @Test
    fun test_search_cities_return_not_null() {

        privateCitiesField.set("cities", getMockedCities())
        val citiesList = privateCitiesField.get(citiesUtility) as ArrayList<*>

        assertNotNull(citiesList)
    }

    @Test
    fun test_search_cities_size_return_true() {
    }

    @Test
    fun test_search_cities_size_return_false() {
    }

    @Test
    fun test_search_cities_return_null() {
    }

    @Test
    fun test_search_cities_return_non_null() {
    }

    @Test
    fun test_search_cities_size_return_greater_then_zero() {
    }


    private fun getMockedCities(): List<City> {

        val citiesMocke = mutableListOf<City>()

        val dubai = City()
        citiesMocke.add(dubai)
        val sharjah = City()
        citiesMocke.add(dubai)
        val ajman = City()
        citiesMocke.add(dubai)

        return citiesMocke.toList()
    }
}
