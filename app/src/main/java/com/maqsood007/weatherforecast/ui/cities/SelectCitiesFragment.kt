package com.maqsood007.weatherforecast.ui.cities

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ClickableSpan
import android.text.style.UnderlineSpan
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.maqsood007.weatherforecast.AppConstants.SELECTED_CITY
import com.maqsood007.weatherforecast.R
import com.maqsood007.weatherforecast.databinding.FragmentSelectCitiesBinding
import com.test.nyt_most_viewed.ui.base.BaseFragment
import kotlinx.android.synthetic.main.error_layout.view.*
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 */
class SelectCitiesFragment : BaseFragment() {


    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var citiesViewModel: CitiesViewModel

    private var searchView: SearchView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val fragmentSelectCitiesBinding: FragmentSelectCitiesBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_select_cities, container, false
        )


        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        fragmentSelectCitiesBinding.recyclerView.layoutManager = layoutManager
        fragmentSelectCitiesBinding.recyclerView.setHasFixedSize(true)

        val dividerItemDecoration = DividerItemDecoration(
            fragmentSelectCitiesBinding.recyclerView.getContext(),
            layoutManager.getOrientation()
        )
        fragmentSelectCitiesBinding.recyclerView.addItemDecoration(dividerItemDecoration)

        citiesViewModel =
            ViewModelProviders.of(this, viewModelFactory)[CitiesViewModel::class.java]

        citiesViewModel.errorMessage.observe(viewLifecycleOwner, Observer { error ->
            if (error != null) showError(error) else hideError()
        })

        fragmentSelectCitiesBinding.lifecycleOwner = this

        citiesViewModel.loadingVisibility.observe(viewLifecycleOwner, Observer { visibility ->

            if (visibility == View.VISIBLE) {
                // show the progress loader
                fragmentSelectCitiesBinding.progressBar.visibility = visibility

            } else {
                // hide the progress loader
                fragmentSelectCitiesBinding.progressBar.visibility = visibility
            }

        })

        citiesViewModel.errorLayoutVisibility.observe(viewLifecycleOwner, Observer { visibility ->

            fragmentSelectCitiesBinding.errorLayout.visibility = visibility
        })

        fragmentSelectCitiesBinding.btnSearch.setOnClickListener {
            navigateToSearch()
        }


        if (citiesViewModel.citiesList.value == null) {
            citiesViewModel.getCities()
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


        fragmentSelectCitiesBinding.errorLayout.tvErrorTitle.text = ssb
        fragmentSelectCitiesBinding.errorLayout.tvErrorTitle.setOnClickListener(citiesViewModel.retryListener)

        fragmentSelectCitiesBinding.citiesViewModel = citiesViewModel

        return fragmentSelectCitiesBinding.root
    }


    fun navigateToSearch() {

        val bundle =
            bundleOf(SELECTED_CITY to citiesViewModel.cityListAdapter.getSelectedCities())

        findNavController().navigate(
            R.id.action_selectCitiesFragment_to_forecastByCitiesFragment,
            bundle
        )
    }


    private fun showError(message: String) {
    }

    private fun hideError() {
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search_cities, menu)

        // Associate searchable configuration with the SearchView

        // Associate searchable configuration with the SearchView
        val searchManager =
            context?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu.findItem(R.id.action_search)
            .actionView as SearchView?
        searchView?.setSearchableInfo(
            searchManager
                .getSearchableInfo(activity?.componentName)
        )
        searchView?.setMaxWidth(Int.MAX_VALUE)

        // listening to search query text change

        // listening to search query text change
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d("onQueryTextSubmit", query!!)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.d("onQueryTextChange", newText!!)
                citiesViewModel.searchCities(newText)
                return false
            }
        })

        searchView?.setOnCloseListener(object : SearchView.OnCloseListener {
            override fun onClose(): Boolean {
                Log.d("setOnCloseListener", "closed")
                citiesViewModel.searchClosed()
                return false
            }
        })

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("lifeCycle", "onDestroy")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("lifeCycle", "onDestroy")
    }
}
