package com.maqsood007.weatherforecast.ui.cities

import android.app.Application
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.maqsood007.weatherforecast.WeatherApp
import com.maqsood007.weatherforecast.data.response.cities.City
import com.maqsood007.weatherforecast.ui.cities.adapter.CitiesListAdapter
import com.maqsood007.weatherforecast.utils.CitiesUtility
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by Muhammad Maqsood on 06/04/2020.
 */
class CitiesViewModel @Inject constructor(private val weatherApp: WeatherApp) : ViewModel() {

    val cityListAdapter = CitiesListAdapter()

    val citiesList = MutableLiveData<List<City?>>()

    var citiesListToShow = listOf<City?>()

    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorLayoutVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorMessage: MutableLiveData<String> = MutableLiveData()

    lateinit var subscription: Disposable

    init {
        cityListAdapter.addCitiesSelectionCountObserver()
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
        cityListAdapter.clearCitiesSelectionCountObserver()
    }

    val retryListener = View.OnClickListener {
        getCities()
    }

    public fun getCities() {

        subscription =
            CitiesUtility.getCities(weatherApp)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { onCitiesFetchStart() }
                .doOnTerminate { onCitiesFetchEnd() }
                .subscribe(this::handleResponse, this::handleError)

    }

    private fun onCitiesFetchStart() {

        loadingVisibility.value = View.VISIBLE

        // Remove error on loading start
        errorMessage.value = null
        errorLayoutVisibility.value = View.GONE
    }

    private fun onCitiesFetchEnd() {
        loadingVisibility.value = View.GONE
    }


    private fun handleResponse(lisOfCities: List<City?>?) {
        citiesList.value = lisOfCities
        citiesListToShow = citiesList.value!!
        updateCitiesAdapter()
    }


    private fun updateCitiesAdapter() {
        cityListAdapter.updateCities(citiesListToShow)
    }

    fun searchCities(query: String?) {
        query?.apply {
            val cities = CitiesUtility.searchCities(query)
            citiesListToShow = cities!!
            updateCitiesAdapter()
        }
    }

    fun searchClosed() {
        citiesListToShow = citiesList.value!!
        updateCitiesAdapter()
    }

    private fun handleError(error: Throwable) {
        errorMessage.value = error.message

        if (citiesList.value == null || citiesList.value?.size == 0)
            errorLayoutVisibility.value = View.VISIBLE

    }

}