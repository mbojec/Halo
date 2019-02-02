package com.mbojec.halo.ui

import android.app.Activity
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.mbojec.halo.R
import com.mbojec.halo.SearchCityListAdapter
import com.mbojec.halo.dagger.Injectable
import com.mbojec.halo.model.Status
import com.mbojec.halo.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.search_fragment.*
import javax.inject.Inject


class SearchFragment : Fragment(), Injectable {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject lateinit var viewModel: SearchViewModel
    private var searchView: SearchView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.search_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SearchViewModel::class.java)
        submitViewModel()
    }

    override fun onResume() {
        super.onResume()
        floatingActionButton2.setOnClickListener {
            val navController = findNavController()
            navController.navigate(R.id.main_dest)
        }
    }

    override fun onStart() {
        super.onStart()
        searchView?.let { viewModel.createSearchViewObserver(searchView!!)
            searchView!!.isIconified = false}
        SearchCityListAdapter.getInstanceAndInit(this, viewModel, activity!!)
    }

    override fun onStop() {
        super.onStop()
        viewModel.hideSoftKeyboard(activity as Activity)
        SearchCityListAdapter.clearInstance()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search, menu)
        super.onCreateOptionsMenu(menu, inflater)
        searchView = ((menu.findItem(R.id.app_bar_search)).actionView as SearchView)
        searchView!!.isIconified = false
        viewModel.createSearchViewObserver(searchView!!)
    }

    private fun submitViewModel(){
        viewModel.responseStatus.observe(this, Observer {
            it?.let { it ->
                when(it.status){
                Status.SUCCESS -> progressBar.visibility = View.INVISIBLE
                Status.FAILURE -> progressBar.visibility = View.INVISIBLE
                Status.ERROR -> progressBar.visibility = View.INVISIBLE
                Status.LOADING ->progressBar.visibility = View.VISIBLE
            } }
        })
    }
}
