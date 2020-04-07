package com.maqsood007.weatherforecast.ui.forcasts.current_location

import android.os.Bundle
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ClickableSpan
import android.text.style.UnderlineSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.maqsood007.weatherforecast.R
import com.maqsood007.weatherforecast.databinding.FragmentForcastByLocationBinding
import com.maqsood007.weatherforecast.ui.forcasts.WeatherForecastViewModel
import com.test.nyt_most_viewed.ui.base.BaseFragment
import kotlinx.android.synthetic.main.error_layout.view.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class ForcastByLocationFragment : BaseFragment() {


    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var weatherForecastViewModel: WeatherForecastViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val fragmentForcastByLocationBinding: FragmentForcastByLocationBinding =
            DataBindingUtil.inflate(
                inflater, R.layout.fragment_forcast_by_location, container, false
            )

        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        fragmentForcastByLocationBinding.recyclerView.layoutManager = layoutManager
        fragmentForcastByLocationBinding.recyclerView.setHasFixedSize(true)

        val dividerItemDecoration = DividerItemDecoration(
            fragmentForcastByLocationBinding.recyclerView.getContext(),
            layoutManager.getOrientation()
        )
        fragmentForcastByLocationBinding.recyclerView.addItemDecoration(dividerItemDecoration)

        weatherForecastViewModel =
            ViewModelProviders.of(this, viewModelFactory)[WeatherForecastViewModel::class.java]

        weatherForecastViewModel.errorMessage.observe(viewLifecycleOwner, Observer { error ->
            if (error != null) showError(error) else hideError()
        })

        fragmentForcastByLocationBinding.lifecycleOwner = this

        weatherForecastViewModel.loadingVisibility.observe(
            viewLifecycleOwner,
            Observer { visibility ->

                if (visibility == View.VISIBLE) {
                    // show the progress loader
                    fragmentForcastByLocationBinding.progressBar.visibility = visibility

                } else {
                    // hide the progress loader
                    fragmentForcastByLocationBinding.progressBar.visibility = visibility
                }

            })

        weatherForecastViewModel.errorLayoutVisibility.observe(
            viewLifecycleOwner,
            Observer { visibility ->

                fragmentForcastByLocationBinding.errorLayout.visibility = visibility
            })


        if (weatherForecastViewModel.locationForecastData.value == null) {
            weatherForecastViewModel.getForecastByLocation()
        }


        val errorMessage =
            SpannableString(resources.getString(R.string.something_went_wrong))

        val clickHere =
            SpannableString(resources.getString(R.string.click_here))

        clickHere.setSpan(UnderlineSpan(), 0, clickHere.length, 0)

        val clickHereText: ClickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                Toast.makeText(activity, "Clicked", Toast.LENGTH_LONG).show()
            }
        }

        clickHere.setSpan(clickHereText, 0, 10, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        val ssb = SpannableStringBuilder()
        ssb.append(errorMessage)
        ssb.append(" ")
        ssb.append(clickHere)


        fragmentForcastByLocationBinding.errorLayout.tvErrorTitle.text = ssb
        fragmentForcastByLocationBinding.errorLayout.tvErrorTitle.setOnClickListener(
            weatherForecastViewModel.retryListener
        )

        fragmentForcastByLocationBinding.forecastViewModel = weatherForecastViewModel

        return fragmentForcastByLocationBinding.root
    }

    private fun showError(message: String) {
    }

    private fun hideError() {
    }
}
