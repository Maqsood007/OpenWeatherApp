package com.maqsood007.weatherforecast.ui.cities

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.maqsood007.weatherforecast.data.response.cities.City
import javax.inject.Inject

/**
 * Created by Muhammad Maqsood on 06/04/2020.
 */
class CitiesListItemViewModel @Inject constructor() : ViewModel() {

    private val name = MutableLiveData<String>()

    private val isSelected = MutableLiveData<Boolean>()


    fun bind(city: City?) {
        name.value = city?.name
        isSelected.value = city?.isSelected
    }

    fun getName(): MutableLiveData<String> {
        return name
    }
    fun isSelected(): MutableLiveData<Boolean> {
        return isSelected
    }

}