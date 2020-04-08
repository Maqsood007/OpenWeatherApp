package com.maqsood007.weatherforecast.ui.forcasts.current_location.adapter

/**
 * @author Muhammad Maqsood.
 */


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.maqsood007.weatherforecast.R
import com.maqsood007.weatherforecast.data.response.currentlocation.ListItem
import com.maqsood007.weatherforecast.databinding.ForecastInfoByTimeListItemBinding
import com.maqsood007.weatherforecast.ui.forcasts.current_location.LocationForecastListItemViewModel

public class LocationForecastListDayAdapter :
    RecyclerView.Adapter<LocationForecastListDayAdapter.ViewHolder>() {


    private var locationForeCastDayData = mutableListOf<ListItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val forecastInfoByTimeListItemBinding: ForecastInfoByTimeListItemBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.forecast_info_by_time_list_item, parent, false
            )

        return ViewHolder(forecastInfoByTimeListItemBinding)
    }

    override fun getItemCount(): Int {
        return locationForeCastDayData.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val dataByTime = locationForeCastDayData.get(position)

        holder.bind(dataByTime)

        holder.itemView.setOnClickListener {

        }
    }

    fun addDayTimeForCast(forecastDayData: MutableList<ListItem>) {
        locationForeCastDayData = forecastDayData
        notifyDataSetChanged()
    }

    fun clearOldData() {
        locationForeCastDayData.clear()
    }

    class ViewHolder(private val forecastInfoByTimeListItemBinding: ForecastInfoByTimeListItemBinding) :
        RecyclerView.ViewHolder(forecastInfoByTimeListItemBinding.root) {
        private val viewModel = LocationForecastListItemViewModel()

        fun bind(result: ListItem?) {
            viewModel.bind(result, false)
            forecastInfoByTimeListItemBinding.locationForecastListItemViewModel = viewModel
        }

    }


}