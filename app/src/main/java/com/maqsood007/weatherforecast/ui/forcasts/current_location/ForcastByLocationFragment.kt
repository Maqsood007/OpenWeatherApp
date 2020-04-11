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

        fragmentForcastByLocationBinding.lifecycleOwner = this

        weatherForecastViewModel.areaSearched.observe(
            viewLifecycleOwner,
            Observer {
                // Set title for the area searched.
                currentCityName = it
                setToolBarTitle()

            })


        (context as MainActivity).currentLocation.observe(viewLifecycleOwner, Observer {

            if (weatherForecastViewModel.locationForecastData.value == null && weatherForecastViewModel.locationCoordinates.first == 0.0) {

                weatherForecastViewModel.locationCoordinates  = it
                weatherForecastViewModel.getForecastByLocation()
            }

        })

        fragmentForcastByLocationBinding.errorLayout.tvErrorTitle.setOnClickListener(
            weatherForecastViewModel.retryListener
        )

        fragmentForcastByLocationBinding.forecastViewModel = weatherForecastViewModel

        return fragmentForcastByLocationBinding.root
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
