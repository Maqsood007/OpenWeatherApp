package com.maqsood007.weatherforecast.ui.cities

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maqsood007.weatherforecast.WeatherApp
import com.maqsood007.weatherforecast.data.response.cities.City
import com.maqsood007.weatherforecast.ui.base.BaseViewModel
import com.maqsood007.weatherforecast.ui.cities.adapter.CitiesListAdapter
import com.maqsood007.weatherforecast.utils.CitiesUtility
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Muhammad Maqsood on 06/04/2020.
 */
class CitiesViewModel @Inject constructor(weatherApp: WeatherApp) :
    BaseViewModel(weatherApp) {

    val cityListAdapter = CitiesListAdapter()

    val citiesList = MutableLiveData<List<City?>>()
    var citiesListToShow = mutableListOf<City?>()

    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()

    lateinit var subscription: Disposable

    init {
        cityListAdapter.addCitiesSelectionCountObserver()
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
        resetSelection()
        cityListAdapter.clearCitiesSelectionCountObserver()
    }

    val retryListener = View.OnClickListener {
        getCities()
    }

    public fun getCities() {

        subscription =
            CitiesUtility.getCities(weatherApp)
                .subscribeOn(Schedulers.newThread())
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
        citiesListToShow.addAll(citiesList.value!!)
        updateCitiesAdapter()
    }


    private fun updateCitiesAdapter() {
        cityListAdapter.updateCities(citiesListToShow)
    }

    fun searchCities(query: String?) {
        query?.apply {
            val cities = CitiesUtility.searchCities(query)
            citiesListToShow = cities!!.toMutableList()
            updateCitiesAdapter()
        }
    }


    fun resetSelection(){
        citiesListToShow.apply {
            this.forEach {
                it?.isSelected = false
            }
        }
    }

    fun searchClosed() {
        citiesListToShow = citiesList.value!!.toMutableList()
        updateCitiesAdapter()
    }

    private fun handleError(error: Throwable) {
        errorMessage.value = error.message

        if (citiesList.value == null || citiesList.value?.size == 0)
            errorLayoutVisibility.value = View.VISIBLE

    }

}