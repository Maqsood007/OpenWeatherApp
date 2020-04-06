package com.maqsood007.weatherforecast.ui.forcasts.cities

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.maqsood007.weatherforecast.R
import com.test.nyt_most_viewed.ui.base.BaseFragment

/**
 * A simple [Fragment] subclass.
 */
class ForecastByCitiesFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_forecast_by_cities, container, false)
    }

}
