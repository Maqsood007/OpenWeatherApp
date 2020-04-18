package com.maqsood007.weatherforecast.di

import com.maqsood007.weatherforecast.WeatherApp
import com.maqsood007.weatherforecast.di.component.ForecastAppComponent
import com.maqsood007.weatherforecast.di.module.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

/**
 * Created by Muhammad Maqsood on 18/04/2020.
 */

@Singleton
@Component(modules = [AndroidInjectionModule::class, AppModule::class, FragmentModule::class, ViewModelModule::class, ThreadSchedulerTestModule::class, NetworkModule::class, ApiModule::class, ActivityModule::class])
interface WeatherAppTestComponent : ForecastAppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(weatherApp: WeatherApp): Builder

        fun build(): WeatherAppTestComponent

    }
}