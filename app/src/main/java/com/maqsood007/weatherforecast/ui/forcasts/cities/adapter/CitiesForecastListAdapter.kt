package com.maqsood007.weatherforecast.ui.forcasts.cities.adapter

/**
 * @author Muhammad Maqsood.
 */


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.maqsood007.weatherforecast.R
import com.maqsood007.weatherforecast.data.response.currentlocation.ListItem
import com.maqsood007.weatherforecast.databinding.CityForecastInfoListItemBinding
import com.maqsood007.weatherforecast.ui.forcasts.cities.CityForecastListItemViewModel


public class CitiesForecastListAdapter :
    RecyclerView.Adapter<CitiesForecastListAdapter.ViewHolder>() {


    private var citiesForeCastMappedData = listOf<ListItem?>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val cityForecastInfoListItemBinding: CityForecastInfoListItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.city_forecast_info_list_item, parent, false
        )

        return ViewHolder(cityForecastInfoListItemBinding)
    }

    override fun getItemCount(): Int {
        return citiesForeCastMappedData.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val listItem = citiesForeCastMappedData.get(position)

        holder.bind(listItem!!)

        holder.itemView.setOnClickListener {
        }
    }

    fun updateForecastData(forecastData: List<ListItem?>) {
        this.citiesForeCastMappedData = forecastData!!
        notifyDataSetChanged()
    }

    class ViewHolder(private val cityForecastInfoListItemBinding: CityForecastInfoListItemBinding) :
        RecyclerView.ViewHolder(cityForecastInfoListItemBinding.root) {
        private val viewModel = CityForecastListItemViewModel()

        fun bind(listItem: ListItem) {
            viewModel.bind(listItem)

            cityForecastInfoListItemBinding.locationForecastListItemViewModel = viewModel

        }

    }


}