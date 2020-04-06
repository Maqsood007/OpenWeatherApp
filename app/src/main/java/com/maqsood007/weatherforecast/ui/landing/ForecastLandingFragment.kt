package com.maqsood007.weatherforecast.ui.landing

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.maqsood007.weatherforecast.R
import com.test.nyt_most_viewed.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_forcast_landing.*

/**
 * A simple [Fragment] subclass.
 */
class ForecastLandingFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_forcast_landing, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        // Screen navigation listeners
        btnCurrentTempDetails.setOnClickListener { navigateToLocationForecast() }
        btnCityTempDetails.setOnClickListener { navigateToCitiesForecast() }
    }


    // Navigate to Forecast by location
    private fun navigateToLocationForecast(){
        findNavController().navigate(R.id.action_forcastLandingFragment_to_forcastByLocationFragment)
    }

    // Navigate to Forecast by cities
    private fun navigateToCitiesForecast(){
        findNavController().navigate(R.id.action_forcastLandingFragment_to_forecastByCitiesFragment)
    }

}
