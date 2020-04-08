package com.maqsood007.weatherforecast.ui.forcasts.current_location

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.maqsood007.weatherforecast.BuildConfig
import com.maqsood007.weatherforecast.data.response.currentlocation.ListItem
import com.maqsood007.weatherforecast.utils.CommonUtility
import com.maqsood007.weatherforecast.utils.CommonUtility.convertToTitleCaseIteratingChars
import com.maqsood007.weatherforecast.utils.CommonUtility.toTempString
import com.maqsood007.weatherforecast.utils.CommonUtility.toWindSpeed
import com.maqsood007.weatherforecast.utils.DateTimeUtility
import com.squareup.picasso.Picasso
import javax.inject.Inject

/**
 * Created by Muhammad Maqsood on 06/04/2020.
 */
class LocationForecastListItemViewModel @Inject constructor() : ViewModel() {

    private val dayName = MutableLiveData<String>()
    private val winds = MutableLiveData<String>()
    private val description = MutableLiveData<String>()
    private val temprature = MutableLiveData<String>()
    private val imgCloud = MutableLiveData<String>()

    // data for the day view.
    private val time = MutableLiveData<String>()
    private val tempRange = MutableLiveData<String>()

    private val childVisibility = MutableLiveData<Int>()


    private val isSelected = MutableLiveData<Boolean>()


    fun bind(forecastItem: ListItem?, isParent: Boolean = true) {

        if (isParent) {
            childVisibility.value = if (forecastItem?.isExpanded!!) View.VISIBLE else View.GONE
        }

        dayName.value =
            DateTimeUtility.getDayName(forecastItem?.dtTxt!!, DateTimeUtility.DATE_FORMATTER)

        description.value = forecastItem.weather?.get(0)?.description?.convertToTitleCaseIteratingChars()

        winds.value = forecastItem.wind?.toWindSpeed()
        temprature.value = forecastItem.main?.toTempString(CommonUtility.TemperatureType.TEMPERATURE)


        time.value =
            DateTimeUtility.getTime(forecastItem.dtTxt, DateTimeUtility.DATE_FORMATTER)


        tempRange.value = "${forecastItem.main?.toTempString(CommonUtility.TemperatureType.MIN_TEMPERATURE) }~${forecastItem.main?.toTempString(CommonUtility.TemperatureType.MAX_TEMPERATURE) }"


        imgCloud.value =
            "${BuildConfig.BASE_URL_ICON}${forecastItem.weather?.get(0)?.icon}.png"

    }

    fun getDayName(): MutableLiveData<String> {
        return dayName
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

    fun getTime(): MutableLiveData<String> {
        return time
    }

    fun getTempRange(): MutableLiveData<String> {
        return tempRange
    }

    fun isSelected(): MutableLiveData<Boolean> {
        return isSelected
    }

    fun getChildVisibility(): MutableLiveData<Int> {
        return childVisibility
    }

}


@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, imageUrl: String?) {
    Picasso.get()
        .load(imageUrl)
        .into(view)
}

@BindingAdapter("tempRange")
public fun bindText(textView: TextView, tempRange: String) {
    textView.setText(tempRange)
}
