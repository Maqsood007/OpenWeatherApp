package com.maqsood007.weatherforecast.di.module

/**
 * @author Muhammad Maqsood.
 */


import io.reactivex.Scheduler

interface ScheduleProvider {
    fun main(): Scheduler
    fun thread(): Scheduler
}