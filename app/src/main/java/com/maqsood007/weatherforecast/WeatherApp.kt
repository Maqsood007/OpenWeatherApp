package com.maqsood007.weatherforecast

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.multidex.MultiDex
import com.maqsood007.weatherforecast.di.component.DaggerForecastAppComponent
import com.maqsood007.weatherforecast.di.component.ForecastAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

/**
 * Created by Muhammad Maqsood on 06/04/2020.
 */
open class WeatherApp : Application(), HasActivityInjector, HasSupportFragmentInjector {

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun activityInjector(): AndroidInjector<Activity> {
        return activityInjector
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return fragmentInjector
    }


    var forecastAppComponent: ForecastAppComponent? = null

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }


    init {

    }

    override fun onCreate() {
        super.onCreate()

//        DaggerForecastAppComponent
//            .builder()
//            .application(this)
//            .build()
//            .inject(this)

    }


    fun getDaggerForecastComponent(): ForecastAppComponent {

        if (forecastAppComponent == null) {
            this.forecastAppComponent = DaggerForecastAppComponent
                .builder()
                .application(this)
                .build()
        }

        return forecastAppComponent!!

    }


    fun setDaggerForecastComponent(forecastAppComponent: ForecastAppComponent) {
        this.forecastAppComponent = forecastAppComponent
    }
}