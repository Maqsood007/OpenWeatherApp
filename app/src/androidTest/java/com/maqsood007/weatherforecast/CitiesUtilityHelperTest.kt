package com.maqsood007.weatherforecast

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import com.maqsood007.weatherforecast.ui.MainActivity
import com.maqsood007.weatherforecast.utils.CitiesUtility
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

/**
 * Created by Muhammad Maqsood on 06/04/2020.
 */

class CitiesUtilityHelperTest {

    @Rule
    @JvmField
    public var activityScenarioRule: ActivityScenarioRule<MainActivity> =
        ActivityScenarioRule<MainActivity>(
            MainActivity::class.java
        )

    // read cities data from assets folder.
    @Test
    fun readJsonFromAsset() {

        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        Assert.assertNotNull(CitiesUtility.getCities(context = appContext))

    }

}