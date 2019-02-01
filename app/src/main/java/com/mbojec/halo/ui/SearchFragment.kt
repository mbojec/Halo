package com.mbojec.halo.ui

import android.app.Activity
import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mbojec.halo.R
import com.mbojec.halo.SearchCityListAdapter
import com.mbojec.halo.dagger.Injectable
import com.mbojec.halo.model.Status
import com.mbojec.halo.utils.NetworkUtils
import com.mbojec.halo.viewmodel.SearchViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.search_fragment.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class SearchFragment : Fragment(), Injectable {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject lateinit var viewModel: SearchViewModel

    private lateinit var disposable: Disposable
    private var searchView: SearchView? = null
    private var adapter: SearchCityListAdapter? = null

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
        searchView?.let { createSearchViewObserver()
            searchView!!.isIconified = false}
    }

    override fun onStop() {
        super.onStop()
        if (!disposable.isDisposed) {
            disposable.dispose()
        }
        hideSoftKeyboard()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search, menu)
        super.onCreateOptionsMenu(menu, inflater)
        searchView = ((menu.findItem(R.id.app_bar_search)).actionView as SearchView)
        searchView!!.isIconified = false
        createSearchViewObserver()
    }

    private fun submitViewModel(){
        viewModel.searchCityList.observe(this, Observer {it ->
            if (adapter == null){
                it?.let { adapter = SearchCityListAdapter(it)}.also {createAdapter()}
            } else {
                it?.let { adapter!!.updateList(it) }.also { adapter!!.notifyDataSetChanged() }
            }
        })
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

    private fun createAdapter(){
        val layoutManager = LinearLayoutManager(activity!!, RecyclerView.VERTICAL, false)
        searchCityListRecycleView.layoutManager = layoutManager
        searchCityListRecycleView.adapter = adapter
    }

    private fun createSearchViewObserver(){
        val searchViewTextObservable = createSearchViewObservable()
        disposable = searchViewTextObservable
            .subscribeOn(AndroidSchedulers.mainThread())
            .doOnNext {}
            .map { it.trim { it <= ' ' } }
            .subscribe {
                if (NetworkUtils.isNetworkConnected(activity as Activity)){
                    viewModel.searchCity(it)
                }
            }
    }

    private fun createSearchViewObservable(): Observable<String> {

        val textChangeObservable = Observable.create<String> { emitter ->
            val onQueryTextListener = object : SearchView.OnQueryTextListener {
                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText.isNullOrEmpty()){
                        clearList()
                    } else {
                        emitter.onNext(newText)
                    }
                    return false
                }
                override fun onQueryTextSubmit(query: String?): Boolean {
                    query?.let { emitter.onNext(query) }
                    return false
                }
            }
            searchView!!.setOnQueryTextListener(onQueryTextListener)

            emitter.setCancellable {
                searchView!!.setOnQueryTextListener(null)
            }
        }

        return textChangeObservable
            .filter { it.length > 2 }
            .debounce(500, TimeUnit.MILLISECONDS)
    }

    private fun hideSoftKeyboard(){
        val inputMethodManager = (activity as Activity).getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow((activity as Activity).currentFocus?.windowToken, 0)
    }

    private fun clearList(){
        adapter?.let{ it.clearList()}.let { adapter?.notifyDataSetChanged()  }
    }
}
