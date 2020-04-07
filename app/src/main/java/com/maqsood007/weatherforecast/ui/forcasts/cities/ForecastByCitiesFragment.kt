package com.maqsood007.weatherforecast.ui.forcasts.cities

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
import com.maqsood007.weatherforecast.databinding.FragmentForecastByCitiesBinding
import com.maqsood007.weatherforecast.ui.forcasts.WeatherForecastViewModel
import com.maqsood007.weatherforecast.utils.CommonUtility
import com.maqsood007.weatherforecast.utils.CommonUtility.toTempString
import com.maqsood007.weatherforecast.utils.CommonUtility.toWindSpeed
import com.test.nyt_most_viewed.ui.base.BaseFragment
import kotlinx.android.synthetic.main.error_layout.view.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class ForecastByCitiesFragment : BaseFragment() {


    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var citiesForecastViewModel: CitiesForecastViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val fragmentForecastByCitiesBinding: FragmentForecastByCitiesBinding =
            DataBindingUtil.inflate(
                inflater, R.layout.fragment_forecast_by_cities, container, false
            )

        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        fragmentForecastByCitiesBinding.recyclerView.layoutManager = layoutManager
        fragmentForecastByCitiesBinding.recyclerView.setHasFixedSize(true)

        val dividerItemDecoration = DividerItemDecoration(
            fragmentForecastByCitiesBinding.recyclerView.getContext(),
            layoutManager.getOrientation()
        )
        fragmentForecastByCitiesBinding.recyclerView.addItemDecoration(dividerItemDecoration)

        citiesForecastViewModel =
            ViewModelProviders.of(this, viewModelFactory)[CitiesForecastViewModel::class.java]

        citiesForecastViewModel.errorMessage.observe(viewLifecycleOwner, Observer { error ->
            if (error != null) showError(error) else hideError()
        })

        fragmentForecastByCitiesBinding.lifecycleOwner = this

        citiesForecastViewModel.loadingVisibility.observe(
            viewLifecycleOwner,
            Observer { visibility ->

                if (visibility == View.VISIBLE) {
                    // show the progress loader
                    fragmentForecastByCitiesBinding.progressBar.visibility = visibility

                } else {
                    // hide the progress loader
                    fragmentForecastByCitiesBinding.progressBar.visibility = visibility
                }

            })


        citiesForecastViewModel.errorLayoutVisibility.observe(
            viewLifecycleOwner,
            Observer { visibility ->

                fragmentForecastByCitiesBinding.errorLayout.visibility = visibility
            })


        if (citiesForecastViewModel.citiesForecastData.value == null) {
            citiesForecastViewModel.getForecastByCities("")
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


        fragmentForecastByCitiesBinding.errorLayout.tvErrorTitle.text = ssb
        fragmentForecastByCitiesBinding.errorLayout.tvErrorTitle.setOnClickListener(
            citiesForecastViewModel.retryListener
        )

        fragmentForecastByCitiesBinding.citiesForecastViewModel = citiesForecastViewModel

        return fragmentForecastByCitiesBinding.root
    }

    private fun showError(message: String) {
    }

    private fun hideError() {
    }

}
