package com.mbojec.halo.ui

import android.app.Activity
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mbojec.halo.HaloApplication
import com.mbojec.halo.R
import com.mbojec.halo.adapters.SearchCityListAdapter
import com.mbojec.halo.dagger.Injectable
import com.mbojec.halo.model.Status
import com.mbojec.halo.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.search_fragment.*
import javax.inject.Inject


class SearchFragment : Fragment(), Injectable {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject lateinit var viewModel: SearchViewModel
    @Inject lateinit var application: HaloApplication
    private var searchView: SearchView? = null
    private var recyclerView: RecyclerView? = null
    private lateinit var adapter: SearchCityListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.search_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SearchViewModel::class.java)
        submitViewModel()
    }

    override fun onStart() {
        super.onStart()
        searchView?.let { viewModel.createSearchViewObserver(searchView!!)
            searchView!!.isIconified = false}
        setAdapter()
    }

    override fun onStop() {
        super.onStop()
        viewModel.hideSoftKeyboard(activity as Activity)
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

        viewModel.searchCityList.observe(this, Observer {it ->
            it?.let { adapter.loadList(it) }?: adapter.clearList()
        })
    }

    private fun setAdapter(){
        recyclerView = searchCityListRecycleView
        recyclerView?.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        adapter = SearchCityListAdapter(application)
        searchCityListRecycleView.adapter = adapter
    }
}
