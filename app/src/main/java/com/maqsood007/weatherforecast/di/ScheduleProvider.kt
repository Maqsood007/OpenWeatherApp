package com.test.nyt_most_viewed.di

/**
 * @author Muhammad Maqsood.
 */


import io.reactivex.Scheduler

interface ScheduleProvider {
    fun main(): Scheduler
    fun thread(): Scheduler
}