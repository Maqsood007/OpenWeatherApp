package com.maqsood007.weatherforecast.di.module

import android.content.Context
import com.maqsood007.weatherforecast.WeatherApp
import com.maqsood007.weatherforecast.data.local.AppPreference
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    internal fun provideSharedPreferences(weatherApp: WeatherApp): AppPreference {
        return AppPreference(weatherApp.getSharedPreferences("weatherPreference", Context.MODE_PRIVATE))
    }

}