package com.maqsood007.weatherforecast.utils

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.maqsood007.weatherforecast.data.response.cities.City
import com.maqsood007.weatherforecast.utils.CitiesUtility.readCategoryFromAsset
import io.reactivex.Observable
import java.io.InputStream

/**
 * Created by Muhammad Maqsood on 06/04/2020.
 */
object CitiesUtility {


    private val FILE_NAME = "city-list.json"
    private var cities: List<City?>? = null


    fun getCities(context: Context): Observable<List<City?>?> {

        cities?.let {
            return Observable.fromCallable {
                it
            }
        }

        return Observable.fromCallable {
            cities = readCategoryFromAsset(context)
            return@fromCallable cities
        }


    }


    fun searchCities(query: String): List<City?>? {

        cities?.let {
            return cities?.filter { it?.name?.contains(query, ignoreCase = true)!! }
        }

        return null
    }


    fun readCategoryFromAsset(context: Context): List<City?> {

        val inputStream: InputStream = context.assets.open(FILE_NAME)
        val size = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        val turnsType = object : TypeToken<List<City>>() {}.type

        return Gson().fromJson<List<City>>(String(buffer, Charsets.UTF_8), turnsType)

    }

}