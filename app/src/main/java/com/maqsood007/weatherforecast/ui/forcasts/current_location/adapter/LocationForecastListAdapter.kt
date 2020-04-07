package com.maqsood007.weatherforecast.ui.forcasts.current_location.adapter

/**
 * @author Muhammad Maqsood.
 */


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.maqsood007.weatherforecast.R
import com.maqsood007.weatherforecast.data.response.currentlocation.ListItem
import com.maqsood007.weatherforecast.databinding.ForecastInfoListItemBinding
import com.maqsood007.weatherforecast.ui.forcasts.current_location.LocationForecastListItemViewModel


public class LocationForecastListAdapter :
    RecyclerView.Adapter<LocationForecastListAdapter.ViewHolder>() {


    private var locationForeCastMappedData = LinkedHashMap<String, MutableList<ListItem>>()

    private var adapterAdaptiveDate = LinkedHashMap<Int, String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val forecastInfoListItemBinding: ForecastInfoListItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.forecast_info_list_item, parent, false
        )

        return ViewHolder(forecastInfoListItemBinding)
    }

    override fun getItemCount(): Int {
        return adapterAdaptiveDate.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val date = adapterAdaptiveDate.get(position)
        val listOfForecast = locationForeCastMappedData.get(date)

        holder.bind(listOfForecast!!)

        holder.itemView.setOnClickListener {
            val firstData = listOfForecast.get(0)
            firstData.isExpanded = !firstData.isExpanded
            notifyItemChanged(position)
        }
    }

    fun updateForecastData(forecastData: LinkedHashMap<String, MutableList<ListItem>>) {
        this.locationForeCastMappedData = forecastData
        prepareDateForViewHolder()
        notifyDataSetChanged()
    }

    private fun prepareDateForViewHolder() {
        var position = 0;
        this.locationForeCastMappedData.map {
            adapterAdaptiveDate.put(position++, it.key)
        }
    }


    class ViewHolder(private val forecastInfoListItemBinding: ForecastInfoListItemBinding) :
        RecyclerView.ViewHolder(forecastInfoListItemBinding.root) {
        private val viewModel = LocationForecastListItemViewModel()

        fun bind(result: MutableList<ListItem>) {
            viewModel.bind(result.get(0))

            forecastInfoListItemBinding.locationForecastListItemViewModel = viewModel

            val layoutManager =
                LinearLayoutManager(
                    forecastInfoListItemBinding.root.context,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
            forecastInfoListItemBinding.dayRecycleView.layoutManager = layoutManager

            val dayDataAdapter = LocationForecastListDayAdapter().apply {
                addDayTimeForCast(result)
                notifyDataSetChanged()
            }

            with(forecastInfoListItemBinding) {
                forecastInfoListItemBinding.dayDataAdapter = dayDataAdapter
                executePendingBindings()
            }

        }

    }


}