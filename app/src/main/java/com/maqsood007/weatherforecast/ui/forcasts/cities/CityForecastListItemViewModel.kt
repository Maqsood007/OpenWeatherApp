package com.maqsood007.weatherforecast.ui.forcasts.cities

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.maqsood007.weatherforecast.data.response.currentlocation.ListItem
import com.maqsood007.weatherforecast.utils.CommonUtility.toTempString
import com.maqsood007.weatherforecast.utils.CommonUtility.toWindSpeed
import com.maqsood007.weatherforecast.utils.DateTimeUtility
import com.maqsood007.weatherforecast.BuildConfig
import com.maqsood007.weatherforecast.utils.CommonUtility
import com.maqsood007.weatherforecast.utils.CommonUtility.convertToTitleCaseIteratingChars
import com.squareup.picasso.Picasso
import java.util.*
import javax.inject.Inject

/**
 * Created by Muhammad Maqsood on 06/04/2020.
 */
class CityForecastListItemViewModel @Inject constructor() : ViewModel() {

    private val cityName = MutableLiveData<String>()
    private val winds = MutableLiveData<String>()
    private val description = MutableLiveData<String>()
    private val temprature = MutableLiveData<String>()
    private val imgCloud = MutableLiveData<String>()


    private val isSelected = MutableLiveData<Boolean>()


    fun bind(forecastItem: ListItem?) {

        cityName.value = forecastItem?.name!!

        winds.value = forecastItem.wind?.toWindSpeed()
        temprature.value = forecastItem.main?.toTempString(CommonUtility.TemperatureType.TEMPERATURE)

        description.value = forecastItem.weather?.get(0)?.description?.convertToTitleCaseIteratingChars()


        imgCloud.value =
            "${BuildConfig.BASE_URL_ICON}${forecastItem.weather?.get(0)?.icon}.png"

    }

    fun getCityName(): MutableLiveData<String> {
        return cityName
    }

    fun getWinds(): MutableLiveData<String> {
        return winds
    }

    fun getDescription(): MutableLiveData<String> {
        return description
    }

    fun getTemprature(): MutableLiveData<String> {
        return temprature
    }

    fun getImgCloud(): MutableLiveData<String> {
        return imgCloud
    }

    fun isSelected(): MutableLiveData<Boolean> {
        return isSelected
    }

}
