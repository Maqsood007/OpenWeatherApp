package com.maqsood007.weatherforecast.di.module

/**
 * @author Muhammad Maqsood.
 */


import com.maqsood007.weatherforecast.ui.cities.SelectCitiesFragment
import com.maqsood007.weatherforecast.ui.forcasts.cities.ForecastByCitiesFragment
import com.maqsood007.weatherforecast.ui.forcasts.current_location.ForcastByLocationFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector(modules = [])
    internal abstract fun contributesForcastByLocationFragment(): ForcastByLocationFragment

    @ContributesAndroidInjector(modules = [])
    internal abstract fun contributesForecastByCitiesFragment(): ForecastByCitiesFragment

    @ContributesAndroidInjector(modules = [])
    internal abstract fun contributesSelectCitiesFragment(): SelectCitiesFragment
}