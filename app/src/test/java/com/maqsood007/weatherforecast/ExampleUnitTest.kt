package com.maqsood007.weatherforecast

import android.widget.Button
import com.maqsood007.weatherforecast.utils.bindTextWithCityCounter
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.MockitoAnnotations

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */



@RunWith(JUnit4::class)
class ExampleUnitTest {

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }


    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

}
