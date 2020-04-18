package com.maqsood007.weatherforecast.rule

import android.app.Activity
import android.app.Instrumentation
import androidx.annotation.NonNull
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.maqsood007.weatherforecast.WeatherApp

/**
 * Created by Muhammad Maqsood on 18/04/2020.
 */
class DaggerActivityTestRule<T : Activity> constructor(
    val activityClass: Class<T>?,
    val listener: OnBeforeActivityLaunchedListener<T>?
) : ActivityTestRule<T>(activityClass, false,true) {


    override fun beforeActivityLaunched() {
        super.beforeActivityLaunched()
        listener?.beforeActivityLaunched(
            (InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as WeatherApp),
            activity
        )
    }

    interface OnBeforeActivityLaunchedListener<T> {
        fun beforeActivityLaunched(
            @NonNull weatherApp: WeatherApp,
            @NonNull activity: T?
        )
    }

}