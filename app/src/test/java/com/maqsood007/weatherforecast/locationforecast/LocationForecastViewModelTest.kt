package com.maqsood007.weatherforecast.locationforecast

import RxImmediateSchedulerRule
import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.maqsood007.weatherforecast.data.WeatherApi
import com.maqsood007.weatherforecast.data.response.currentlocation.CurrentLocationForcastResponse
import com.maqsood007.weatherforecast.locationforecast.utils.CitiesForecastMockedUtility
import com.maqsood007.weatherforecast.ui.forcasts.current_location.WeatherForecastViewModel
import io.reactivex.Observable
import io.reactivex.Single
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.net.SocketException
import org.junit.Assert.*

/**
 * Created by Muhammad Maqsood on 08/04/2020.
 */

@RunWith(JUnit4::class)
class LocationForecastViewModelTest {


    @get:Rule
    var rule = InstantTaskExecutorRule()


    companion object {
        @ClassRule
        @JvmField
        val schedulers = RxImmediateSchedulerRule()
    }

    @Mock
    lateinit var weatherApi: WeatherApi

    @Mock
    lateinit var observer: Observer<CurrentLocationForcastResponse>

    var weatherForecastViewModel: WeatherForecastViewModel? = null

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        weatherForecastViewModel = WeatherForecastViewModel(weatherApi)
    }

    // When data loaded successfully
    @Test
    fun test_loadForecastByLocation_Success() {

        val location = Pair("25.346254", "55.420933")

        val mockedResponse = CitiesForecastMockedUtility.getCurrentLocationForecastMockedResponse()

        Mockito.`when`(
            this.weatherApi.getForecastByLocation(location.first, location.second)
        ).thenAnswer {
            return@thenAnswer Observable.just(mockedResponse)
        }

        weatherForecastViewModel?.locationForecastData?.observeForever(observer)
        weatherForecastViewModel?.getForecastByLocation()

        assertNotNull(weatherForecastViewModel?.locationForecastData?.value)

    }

    // When data load is not successful
    @Test
    fun test_loadForecastByLocation_Failure() {

        val location = Pair("25.346254", "55.420933")

        Mockito.`when`(
            this.weatherApi.getForecastByLocation(location.first, location.second)
        ).thenAnswer {
            return@thenAnswer Observable.error<Throwable>(SocketException("NO_INTERNET_MESSAGE"))
        }

        weatherForecastViewModel?.locationForecastData?.observeForever(observer)
        weatherForecastViewModel?.getForecastByLocation()


        assertNull(weatherForecastViewModel?.locationForecastData?.value)

    }


    // When data loaded successfully, We will have a mapped data.
    @Test
    fun test_loadForecastByLocation_MappedData_Success() {

        val location = Pair("25.346254", "55.420933")

        val mockedResponse = CitiesForecastMockedUtility.getCurrentLocationForecastMockedResponse()

        Mockito.`when`(
            this.weatherApi.getForecastByLocation(location.first, location.second)
        ).thenAnswer {
            return@thenAnswer Observable.just(mockedResponse)
        }

        weatherForecastViewModel?.getForecastByLocation()

        assertNotNull(weatherForecastViewModel?.locationForecastMappedData?.value)

    }


    // When data loaded successfully, We will have a mapped data.
    @Test
    fun test_loadForecastByLocation_MappedData_Failure() {

        val location = Pair("25.346254", "55.420933")

        Mockito.`when`(
            this.weatherApi.getForecastByLocation(location.first, location.second)
        ).thenAnswer {
            return@thenAnswer Observable.just(Observable.error<Throwable>(SocketException("NO_INTERNET_MESSAGE")))
        }

        weatherForecastViewModel?.getForecastByLocation()

        assertNotNull(weatherForecastViewModel?.locationForecastMappedData?.value)

    }





    @Test
    fun test_error_visibility_on_load_success() {

        val location = Pair("25.346254", "55.420933")

        val mockedResponse = CitiesForecastMockedUtility.getCurrentLocationForecastMockedResponse()

        Mockito.`when`(
            this.weatherApi.getForecastByLocation(location.first, location.second)
        ).thenAnswer {
            return@thenAnswer Observable.just(mockedResponse)
        }

        weatherForecastViewModel?.locationForecastData?.observeForever(observer)
        weatherForecastViewModel?.getForecastByLocation()

        assertTrue(weatherForecastViewModel?.errorLayoutVisibility?.value != View.VISIBLE)

    }


    @Test
    fun test_error_visibility_on_load_Failure() {

        val location = Pair("25.346254", "55.420933")

        Mockito.`when`(
            this.weatherApi.getForecastByLocation(location.first, location.second)
        ).thenAnswer {
            return@thenAnswer Observable.error<Throwable>(SocketException("NO_INTERNET_MESSAGE"))
        }

        weatherForecastViewModel?.locationForecastData?.observeForever(observer)
        weatherForecastViewModel?.getForecastByLocation()


        assertTrue(weatherForecastViewModel?.errorLayoutVisibility?.value == View.VISIBLE)
    }


    @Test
    fun test_error_message_value_on_load_success() {

        val location = Pair("25.346254", "55.420933")

        val mockedResponse = CitiesForecastMockedUtility.getCurrentLocationForecastMockedResponse()

        Mockito.`when`(
            this.weatherApi.getForecastByLocation(location.first, location.second)
        ).thenAnswer {
            return@thenAnswer Observable.just(mockedResponse)
        }

        weatherForecastViewModel?.locationForecastData?.observeForever(observer)
        weatherForecastViewModel?.getForecastByLocation()

        assertTrue(weatherForecastViewModel?.errorMessage?.value == null)

    }


    @Test
    fun test_error_message_value_on_load_Failure() {

        val location = Pair("25.346254", "55.420933")

        Mockito.`when`(
            this.weatherApi.getForecastByLocation(location.first, location.second)
        ).thenAnswer {
            return@thenAnswer Observable.error<Throwable>(SocketException("NO_INTERNET_MESSAGE"))
        }

        weatherForecastViewModel?.locationForecastData?.observeForever(observer)
        weatherForecastViewModel?.getForecastByLocation()


        assertTrue(weatherForecastViewModel?.errorMessage?.value !=null)
    }


//    @Test
//    fun test_loading_visibility_on_request_responded() {
//
//        val location = Pair("25.346254", "55.420933")
//
//        val mockedResponse = CitiesForecastMockedUtility.getCurrentLocationForecastMockedResponse()
//
//        Mockito.`when`(
//            this.weatherApi.getForecastByLocation(location.first, location.second)
//        ).thenAnswer {
//            return@thenAnswer Observable.just(mockedResponse)
//        }
//
//        weatherForecastViewModel?.locationForecastData.observeForever(observer)
//        weatherForecastViewModel?.getForecastByLocation()
//
//        assertTrue(weatherForecastViewModel?.loadingVisibility.value != View.VISIBLE)
//
//    }


    @After
    @Throws(Exception::class)
    fun tearDown() {
        weatherForecastViewModel = null
    }

}