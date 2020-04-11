package com.maqsood007.weatherforecast.ui.forcasts.current_location

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.maqsood007.weatherforecast.BuildConfig
import com.maqsood007.weatherforecast.WeatherApp
import com.maqsood007.weatherforecast.data.WeatherApi
import com.maqsood007.weatherforecast.data.response.currentlocation.CurrentLocationForcastResponse
import com.maqsood007.weatherforecast.data.response.currentlocation.ListItem
import com.maqsood007.weatherforecast.ui.base.BaseViewModel
import com.maqsood007.weatherforecast.ui.forcasts.current_location.adapter.LocationForecastListAdapter
import com.maqsood007.weatherforecast.utils.CommonUtility
import com.maqsood007.weatherforecast.utils.CommonUtility.convertToTitleCaseIteratingChars
import com.maqsood007.weatherforecast.utils.CommonUtility.toImageUrl
import com.maqsood007.weatherforecast.utils.CommonUtility.toTempString
import com.maqsood007.weatherforecast.utils.CommonUtility.toWindSpeed
import com.maqsood007.weatherforecast.utils.DateTimeUtility
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by Muhammad Maqsood on 06/04/2020.
 */
class WeatherForecastViewModel @Inject constructor(private val weatherApi: WeatherApi, weatherApp: WeatherApp) :
    BaseViewModel(weatherApp) {

    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()

    val areaSearched: MutableLiveData<String> = MutableLiveData()

    //today forecast description images
    val imgDescription: MutableLiveData<String> = MutableLiveData()
    val tvAppNameVisibility: MutableLiveData<Int> = MutableLiveData()
    val tvTemperature: MutableLiveData<String> = MutableLiveData()
    val tvWinds: MutableLiveData<String> = MutableLiveData()
    val tvDescription: MutableLiveData<String> = MutableLiveData()
    val tvTemRange: MutableLiveData<String> = MutableLiveData()
    val tvTodayDate: MutableLiveData<String> = MutableLiveData()

    //today forecast description images
    val imgWindsVisibility: MutableLiveData<Int> = MutableLiveData(View.GONE)
    val imgDescriptionVisibility: MutableLiveData<Int> = MutableLiveData(View.GONE)
    val imgTempRangeVisibility: MutableLiveData<Int> = MutableLiveData(View.GONE)
    val tvTodayDateVisibility: MutableLiveData<Int> = MutableLiveData(View.GONE)


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
            ).subscribeOn(Schedulers.io())
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
        updateUI();
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


    private fun updateUI() {

        val todayForecast = locationForecastData.value?.list?.get(0)
        todayForecast.let {

            val temprature =
                it?.main?.toTempString(CommonUtility.TemperatureType.TEMPERATURE)
            val winds = it?.wind?.toWindSpeed()
            val description = it?.weather?.get(0)?.description
            val tempratureRange =
                "".plus(it?.main?.toTempString(CommonUtility.TemperatureType.MIN_TEMPERATURE))
                    .plus("~")
                    .plus(it?.main?.toTempString(CommonUtility.TemperatureType.MAX_TEMPERATURE))


            imgDescription.value = todayForecast?.weather?.get(0)?.icon?.toImageUrl()
            imgDescriptionVisibility.value = View.VISIBLE
            tvDescription.value =
                description?.convertToTitleCaseIteratingChars()

            tvAppNameVisibility.value = View.GONE
            tvTemperature.value = temprature

            tvWinds.value = winds
            imgWindsVisibility.value = View.VISIBLE

            tvTemRange.value = tempratureRange
            imgTempRangeVisibility.value = View.VISIBLE

            tvTodayDate.value = DateTimeUtility.getTodayFormattedDate()
            tvTodayDateVisibility.value = View.VISIBLE


        }

        areaSearched.value = locationForecastData.value?.city?.name!!
    }

    private fun handleError(error: Throwable) {
        errorMessage.value = error.message

        if (locationForecastData.value == null)
            errorLayoutVisibility.value = View.VISIBLE

    }
}