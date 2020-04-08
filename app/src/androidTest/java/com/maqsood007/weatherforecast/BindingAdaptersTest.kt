package com.maqsood007.weatherforecast

import android.widget.Button
import android.widget.TextView
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import com.maqsood007.weatherforecast.ui.forcasts.current_location.bindText
import com.maqsood007.weatherforecast.ui.MainActivity
import com.maqsood007.weatherforecast.utils.bindTextWithCityCounter
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

/**
 * Created by Muhammad Maqsood on 08/04/2020.
 */

class BindingAdaptersTest {

    @Rule
    @JvmField
    public var activityScenarioRule: ActivityScenarioRule<MainActivity> =
        ActivityScenarioRule<MainActivity>(
            MainActivity::class.java
        )


    @Test
    fun test_setText_Binding_Adapter() {

        val SELECTED_CITY = "5"
        val expactedResult = "SEARCH FORECAST (" + SELECTED_CITY + ")"

        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val button = Button(appContext)

        bindTextWithCityCounter(button = button, citiesSelected = SELECTED_CITY)

        Assert.assertEquals(expactedResult, button.text)
    }


    @Test
    fun test_setText_TextView_Binding_Adapter() {

        val inputText = "Weather App"

        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val textView = TextView(appContext)

        bindText(textView = textView, tempRange = inputText)

        Assert.assertEquals(inputText, textView.text)


    }
}