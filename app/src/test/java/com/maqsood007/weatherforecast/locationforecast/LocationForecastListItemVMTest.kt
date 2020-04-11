package com.maqsood007.weatherforecast.locationforecast

import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.maqsood007.weatherforecast.BuildConfig
import com.maqsood007.weatherforecast.data.response.currentlocation.ListItem
import com.maqsood007.weatherforecast.locationforecast.utils.ForecastMockedDataUtility
import com.maqsood007.weatherforecast.test.RxImmediateSchedulerRule
import com.maqsood007.weatherforecast.ui.forcasts.current_location.LocationForecastListItemViewModel
import com.maqsood007.weatherforecast.utils.CommonUtility
import com.maqsood007.weatherforecast.utils.CommonUtility.convertToTitleCaseIteratingChars
import com.maqsood007.weatherforecast.utils.CommonUtility.toTempString
import com.maqsood007.weatherforecast.utils.CommonUtility.toWindSpeed
import com.maqsood007.weatherforecast.utils.DateTimeUtility
import org.junit.Before
import org.junit.ClassRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.junit.Assert.*

/**
 * Created by Muhammad Maqsood on 11/04/2020.
 */
@RunWith(JUnit4::class)
class LocationForecastListItemVMTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    companion object {
        @ClassRule
        @JvmField
        val schedulers = RxImmediateSchedulerRule()
    }


    lateinit var locationForecastListItemViewModel: LocationForecastListItemViewModel

    private lateinit var forecastItem: ListItem


    @Before
    fun setUp() {
        locationForecastListItemViewModel = LocationForecastListItemViewModel()
        forecastItem = ForecastMockedDataUtility.getIndividualForecastItem()
        locationForecastListItemViewModel.bind(forecastItem)
    }


    @Test
    fun verifyDayNameBinding() {

        val expected = "Wednesday"
        assertEquals(
            locationForecastListItemViewModel.getDayName().value,
            expected
        )
    }

    @Test
    fun verifyWindsBinding() {
        val expected = "4km/h"
        assertEquals(
            locationForecastListItemViewModel.getWinds().value, expected
        )
    }

    @Test
    fun verifyDescriptionBinding() {

        val expected = "Overcast Clouds"

        assertEquals(
            locationForecastListItemViewModel.getDescription().value, expected
        )
    }

    @Test
    fun verifyTempratureBinding() {
        val expected = "10℃"
        assertEquals(
            locationForecastListItemViewModel.getTemprature().value, expected
        )
    }

    @Test
    fun verifyImgCloudBinding() {
        val expected = "https://openweathermap.org/img/w/04d.png"
        assertEquals(
            locationForecastListItemViewModel.getImgCloud().value, expected
        )
    }

    @Test
    fun verifyTimeBinding() {
        val expected = "03:00"
        assertEquals(
            locationForecastListItemViewModel.getTime().value, expected
        )
    }

    @Test
    fun verifyTempRangeBinding() {
        val expected = "10℃~11℃"
        assertEquals(
            locationForecastListItemViewModel.getTempRange().value, expected
        )
    }

    @Test
    fun verifyChildVisibilityBindingWhenExpandedParent() {
        forecastItem.isExpanded = true
        locationForecastListItemViewModel.bind(forecastItem)
        val expected = View.VISIBLE
        assertEquals(
            locationForecastListItemViewModel.getChildVisibility().value, expected
        )
    }

    @Test
    fun verifyChildVisibilityBindingWhenCollapsedParent() {
        forecastItem.isExpanded = false
        locationForecastListItemViewModel.bind(forecastItem)
        val expected = View.GONE
        assertEquals(
            locationForecastListItemViewModel.getChildVisibility().value, expected
        )
    }


    @Test
    fun verifyChildVisibilityBindingChild() {

        locationForecastListItemViewModel.bind(forecastItem, false)

        val expected = View.GONE
        assertEquals(
            locationForecastListItemViewModel.getChildVisibility().value, expected
        )
    }
}