package com.maqsood007.weatherforecast.data

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import com.maqsood007.weatherforecast.BuildConfig
import com.maqsood007.weatherforecast.data.response.citiesforcast.CitiesForcastResponse
import com.maqsood007.weatherforecast.data.response.currentlocation.CurrentLocationForcastResponse
import retrofit2.http.Query

/**
 * Created by Muhammad Maqsood on 06/04/2020.
 */
interface WeatherApi {

    @GET("data/2.5/forecast?appid=${BuildConfig.APP_ID}")
    fun getForecastByLocation(
        @Query("lat") latitude: String,
        @Query("lon") longitude: String
    ): Observable<CurrentLocationForcastResponse>


    @GET("data/2.5/group?appid=${BuildConfig.APP_ID}")
    fun getForecastByCities(@Query("id") cities: String): Observable<CitiesForcastResponse>

}