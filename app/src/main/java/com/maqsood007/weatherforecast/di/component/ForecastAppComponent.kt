package com.maqsood007.weatherforecast.di.component

/**
 * @author Muhammad Maqsood.
 */



import com.maqsood007.weatherforecast.WeatherApp
import com.maqsood007.weatherforecast.di.module.*


import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton


@Singleton
@Component(modules = [AndroidInjectionModule::class, AppModule::class, FragmentModule::class, ViewModelModule::class, NetworkModule::class, ApiModule::class, ActivityModule::class])
interface ForecastAppComponent : AndroidInjector<WeatherApp> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(weatherApp: WeatherApp): Builder

        fun build(): ForecastAppComponent

    }
}