package com.maqsood007.weatherforecast.ui.cities.adapter

/**
 * @author Muhammad Maqsood.
 */


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.maqsood007.weatherforecast.R
import com.maqsood007.weatherforecast.data.response.cities.City
import com.maqsood007.weatherforecast.databinding.CityListItemBinding
import com.maqsood007.weatherforecast.ui.cities.CitiesListItemViewModel

public class CitiesListAdapter :
    RecyclerView.Adapter<CitiesListAdapter.ViewHolder>() {


    private lateinit var citiesList: List<City?>

    val counterToShow = MutableLiveData<String>("0")
    val totalCitiesSelected = MutableLiveData<Int>(0)

    val citiesSelected = mutableListOf<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val cityListItemBinding: CityListItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.city_list_item, parent, false
        )

        return ViewHolder(cityListItemBinding)
    }

    override fun getItemCount(): Int {
        return if (::citiesList.isInitialized) citiesList.size else 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val city = citiesList.get(position)

        holder.bind(city)

        holder.itemView.setOnClickListener {
            if (isValidSelection(city))
                toggleCitySelection(city, position)
        }
    }

    private fun isValidSelection(city: City?): Boolean {
        if (city?.isSelected!!) return true
        return totalCitiesSelected.value!! < 7
    }

    private fun toggleCitySelection(city: City?, position: Int) {
        city?.isSelected = !city?.isSelected!!

        if (city.isSelected) citiesSelected.add(city.id!!) else citiesSelected.remove(element = (city.id))

        totalCitiesSelected.value =
            totalCitiesSelected.value?.plus(if (city.isSelected) 1 else -1)
        notifyItemChanged(position)
    }

    fun updateCities(cities: List<City?>) {
        this.citiesList = cities
        notifyDataSetChanged()
    }

    class ViewHolder(private val cityListItemBinding: CityListItemBinding) :
        RecyclerView.ViewHolder(cityListItemBinding.root) {
        private val viewModel = CitiesListItemViewModel()

        fun bind(result: City?) {
            viewModel.bind(result)
            cityListItemBinding.citiesListItemViewModel = viewModel
        }

    }

    public fun addCitiesSelectionCountObserver() {
        totalCitiesSelected.observeForever(observer);
    }

    public fun clearCitiesSelectionCountObserver() {
        totalCitiesSelected.removeObserver(observer);
    }

    val observer = Observer<Int> {
        counterToShow.value = String().plus(it)
    }


    fun getSelectedCities(): String? {

        var citiesIds: String? = ""
        citiesSelected.apply {

            forEachIndexed { index, item ->
                if (index < (citiesSelected.size - 1)) {
                    citiesIds = citiesIds?.plus(item).plus(",")
                } else citiesIds = citiesIds?.plus(item)
            }
        }

        return citiesIds
    }
}