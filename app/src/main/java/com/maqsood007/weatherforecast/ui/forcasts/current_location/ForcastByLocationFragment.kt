package com.maqsood007.weatherforecast.ui.forcasts.current_location

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.maqsood007.weatherforecast.BuildConfig
import com.maqsood007.weatherforecast.R
import com.maqsood007.weatherforecast.databinding.FragmentForcastByLocationBinding
import com.maqsood007.weatherforecast.extensions.formatErrorLayout
import com.maqsood007.weatherforecast.ui.MainActivity
import com.maqsood007.weatherforecast.ui.base.BaseFragment
import com.maqsood007.weatherforecast.utils.CommonUtility
import com.maqsood007.weatherforecast.utils.CommonUtility.convertToTitleCaseIteratingChars
import com.maqsood007.weatherforecast.utils.CommonUtility.toTempString
import com.maqsood007.weatherforecast.utils.CommonUtility.toWindSpeed
import com.maqsood007.weatherforecast.utils.DateTimeUtility
import kotlinx.android.synthetic.main.error_layout.view.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class ForcastByLocationFragment : BaseFragment() {


    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var weatherForecastViewModel: WeatherForecastViewModel

    private val TAG = "ForcastByLocationFragment"


    private var currentCityName = ""



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

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


        weatherForecastViewModel.locationForecastData.observe(
            viewLifecycleOwner,
            Observer {

                val todayForecast = it?.list?.get(0)

                todayForecast.let {

                    val temprature =
                        it?.main?.toTempString(CommonUtility.TemperatureType.TEMPERATURE)
                    val winds = it?.wind?.toWindSpeed()
                    val description = it?.weather?.get(0)?.description
                    val tempratureRange =
                        "".plus(it?.main?.toTempString(CommonUtility.TemperatureType.MIN_TEMPERATURE))
                            .plus("~")
                            .plus(it?.main?.toTempString(CommonUtility.TemperatureType.MAX_TEMPERATURE))

                    val icon =
                        "${BuildConfig.BASE_URL_ICON}${todayForecast?.weather?.get(0)?.icon}.png"
                    loadImage(fragmentForcastByLocationBinding.imgDescription, icon)

                    fragmentForcastByLocationBinding.tvAppName.visibility = View.GONE

                    fragmentForcastByLocationBinding.tvTemprature.text = temprature
                    fragmentForcastByLocationBinding.imgWinds.visibility = View.VISIBLE
                    fragmentForcastByLocationBinding.tvWinds.text = winds
                    fragmentForcastByLocationBinding.imgDescription.visibility = View.VISIBLE
                    fragmentForcastByLocationBinding.tvDescription.text =
                        description?.convertToTitleCaseIteratingChars()
                    fragmentForcastByLocationBinding.imgTempRange.visibility = View.VISIBLE
                    fragmentForcastByLocationBinding.tvTemRange.text = tempratureRange

                    fragmentForcastByLocationBinding.tvtodayDate.setText(DateTimeUtility.getTodayFormattedDate())
                    fragmentForcastByLocationBinding.tvtodayDate.visibility = View.VISIBLE


                }

                currentCityName = it?.city?.name!!
                setToolBarTitle()
            })


        weatherForecastViewModel.errorLayoutVisibility.observe(
            viewLifecycleOwner,
            Observer { visibility ->

                fragmentForcastByLocationBinding.errorLayout.visibility = visibility
            })


        (context as MainActivity).currentLocation.observe(viewLifecycleOwner, Observer {

            if (weatherForecastViewModel.locationForecastData.value == null && weatherForecastViewModel.locationCoordinates.first == 0.0) {

                weatherForecastViewModel.locationCoordinates  = it
                weatherForecastViewModel.getForecastByLocation()
            }

        })


        fragmentForcastByLocationBinding.errorLayout.tvErrorTitle.formatErrorLayout()
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


    private val listener =
        NavController.OnDestinationChangedListener { controller, destination, arguments ->
            // react on change
            // you can check destination.id or destination.label and act based on that
            print(TAG)
            if ((destination as FragmentNavigator.Destination).className.contains(TAG)) {
                setToolBarTitle(currentCityName)
            }
        }

    private fun setToolBarTitle(title: String = "") {
        val screenTitle = if (title.isEmpty()) currentCityName else title
        (activity as AppCompatActivity?)?.getSupportActionBar()
            ?.setTitle(screenTitle.convertToTitleCaseIteratingChars())
    }

    override fun onResume() {
        super.onResume()
        findNavController().addOnDestinationChangedListener(listener)
    }

    override fun onPause() {
        super.onPause()
        findNavController().removeOnDestinationChangedListener(listener)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> {
                findNavController().navigate(R.id.action_forcastByLocationFragment_to_selectCitiesFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


}
