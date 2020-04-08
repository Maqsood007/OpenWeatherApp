package com.maqsood007.weatherforecast.ui.forcasts.current_location

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.maqsood007.weatherforecast.data.WeatherApi
import com.maqsood007.weatherforecast.data.response.currentlocation.CurrentLocationForcastResponse
import com.maqsood007.weatherforecast.data.response.currentlocation.ListItem
import com.maqsood007.weatherforecast.ui.forcasts.current_location.adapter.LocationForecastListAdapter
import com.maqsood007.weatherforecast.utils.DateTimeUtility
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by Muhammad Maqsood on 06/04/2020.
 */
class WeatherForecastViewModel @Inject constructor(private val weatherApi: WeatherApi) :
    ViewModel() {

    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorLayoutVisibility: MutableLiveData<Int> = MutableLiveData(View.GONE)
    val errorMessage: MutableLiveData<String> = MutableLiveData()


    val locationForecastData = MutableLiveData<CurrentLocationForcastResponse>()
    val locationForecastMappedData = MutableLiveData<LinkedHashMap<String, MutableList<ListItem>>>()

    val forecastAdapter = LocationForecastListAdapter()

    var subscription: Disposable? = null

    override fun onCleared() {
        super.onCleared()
        subscription?.dispose()
    }

    init {
        val mutableMap = LinkedHashMap<String, MutableList<ListItem>>()
        locationForecastMappedData.value = mutableMap
    }

    var locationCoordinates = Pair<Double, Double>(0.0, 0.0)

    val retryListener = View.OnClickListener {
        getForecastByLocation()
    }

    public fun getForecastByLocation() {

        subscription =
            weatherApi.getForecastByLocation(
                locationCoordinates.first.toString(),
                locationCoordinates.second.toString()
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { onForecastFetchStart() }
                .doOnTerminate { onForecastFetchEnd() }
                .subscribe(this::handleLocationForecastResponse, this::handleError)

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


    private fun handleLocationForecastResponse(locationForecastResponse: CurrentLocationForcastResponse?) {
        locationForecastData.value = locationForecastResponse
        mapForecastDate()
    }


    private fun mapForecastDate() {

        locationForecastData.value?.apply {

            val forecastDate = list!!
            for (dateItem in forecastDate) {

                // Get date from list item while iteration
                val date =
                    DateTimeUtility.getDateOnly(dateItem?.dtTxt!!, DateTimeUtility.DATE_FORMATTER)

                // add item to of map already contain that date data
                if (locationForecastMappedData.value?.containsKey(date)!!) {
                    val listItems = locationForecastMappedData.value?.get(date)
                    listItems?.add(dateItem)
                } else {
                    // other wise add a new list of date to map.
                    val listItems = mutableListOf<ListItem>()
                    listItems.add(dateItem)
                    locationForecastMappedData.value?.put(date, listItems)
                }
            }

        }

        forecastAdapter.updateForecastData(forecastData = locationForecastMappedData.value!!)

        Log.d("MAPPED_DATA", locationForecastMappedData.toString())

    }

    private fun handleError(error: Throwable) {
        errorMessage.value = error.message

        if (locationForecastData.value == null)
            errorLayoutVisibility.value = View.VISIBLE

    }
}