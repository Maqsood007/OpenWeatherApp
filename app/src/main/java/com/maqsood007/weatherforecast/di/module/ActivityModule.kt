package com.test.nyt_most_viewed.di.module

import com.maqsood007.weatherforecast.utils.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    internal abstract fun contributeMainActivity(): MainActivity
}

