package com.test.nyt_most_viewed.di.module

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.maqsood007.weatherforecast.WeatherApp
import com.test.nyt_most_viewed.data.local.AppPreference
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    internal fun provideSharedPreferences(weatherApp: WeatherApp): AppPreference {
        return AppPreference(weatherApp.getSharedPreferences("nytPreference", Context.MODE_PRIVATE))
    }

}