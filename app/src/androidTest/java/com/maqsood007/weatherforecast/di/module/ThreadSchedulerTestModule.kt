package com.maqsood007.weatherforecast.di.module

import android.os.AsyncTask
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Created by Muhammad Maqsood on 18/04/2020.
 */
@Module
class ThreadSchedulerTestModule {


    @Provides
    @Singleton
    fun schedules(): ScheduleProvider = object : ScheduleProvider {
        override fun main(): Scheduler {
            return AndroidSchedulers.mainThread()
        }

        override fun thread(): Scheduler {
            return Schedulers.from(AsyncTask.THREAD_POOL_EXECUTOR)
        }

    }

}