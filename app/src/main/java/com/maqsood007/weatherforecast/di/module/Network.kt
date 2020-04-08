package com.maqsood007.weatherforecast.di.module

/**
 * @author Muhammad Maqsood.
 */


import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.maqsood007.weatherforecast.BuildConfig
import com.maqsood007.weatherforecast.data.WeatherApi
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun schedules(): ScheduleProvider = object : ScheduleProvider {
        override fun main(): Scheduler {
            return AndroidSchedulers.mainThread()
        }

        override fun thread(): Scheduler {
            return Schedulers.io()
        }

    }

    @Singleton
    @Provides
    fun okHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun converterFactory(): Converter.Factory = GsonConverterFactory.create()

    @Singleton
    @Provides
    fun retrofit(httpClient: OkHttpClient, converterFactory: Converter.Factory): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(httpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(converterFactory)
            .build()
    }

}

@Module
class ApiModule {

    @Singleton
    @Provides
    internal fun weatherForecastAPI(retrofit: Retrofit): WeatherApi = retrofit.create(WeatherApi::class.java)
}