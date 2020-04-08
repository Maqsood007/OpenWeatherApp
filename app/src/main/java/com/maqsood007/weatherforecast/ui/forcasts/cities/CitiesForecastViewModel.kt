package com.maqsood007.weatherforecast.ui.forcasts.cities

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.maqsood007.weatherforecast.data.WeatherApi
import com.maqsood007.weatherforecast.data.response.citiesforcast.CitiesForcastResponse
import com.maqsood007.weatherforecast.ui.forcasts.cities.adapter.CitiesForecastListAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by Muhammad Maqsood on 06/04/2020.
 */
class CitiesForecastViewModel @Inject constructor(private val weatherApi: WeatherApi) :
    ViewModel() {

    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorLayoutVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorMessage: MutableLiveData<String> = MutableLiveData()


    val citiesForecastData = MutableLiveData<CitiesForcastResponse>()

    val forecastAdapter = CitiesForecastListAdapter()

    var subscription: Disposable? = null

    lateinit var citiesSearched : String

    override fun onCleared() {
        super.onCleared()
        subscription?.dispose()
    }

    val retryListener = View.OnClickListener {
        getForecastByCities(citiesSearched)
    }


    fun getForecastByCities(cities: String) {

        citiesSearched = cities
        subscription =
            weatherApi.getForecastByCities(cities = citiesSearched)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { onForecastFetchStart() }
                .doOnTerminate { onForecastFetchEnd() }
                .subscribe(this::handleCitiesForecastResponse, this::handleError)

    }


    private fun onForecastFetchStart() {

        loadingVisibility.value = View.VISIBLE

        // Remove error on loading start
        errorMessage.value = null
        errorLayoutVisibility.value = View.GONE
    }

    private fun onForecastFetchEnd() {
        loadingVisibility.value = View.GONE
    }

    private fun handleCitiesForecastResponse(citiesForecastResponse: CitiesForcastResponse) {
        citiesForecastData.value = citiesForecastResponse
        forecastAdapter.updateForecastData(citiesForecastResponse.list!!)
        forecastAdapter.notifyDataSetChanged()
    }

    private fun handleError(error: Throwable) {
        errorMessage.value = error.message

        if (citiesForecastData.value == null)
            errorLayoutVisibility.value = View.VISIBLE

    }
}