package com.mbojec.halo.ui

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import com.mbojec.halo.viewmodel.MainViewModel
import com.mbojec.halo.R
import com.mbojec.halo.dagger.Injectable
import com.mbojec.halo.utils.ForecastAdapter
import kotlinx.android.synthetic.main.main_fragment.*
import javax.inject.Inject


class MainFragment : Fragment(), Injectable {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject lateinit var viewModel: MainViewModel
    private var adapter: ForecastAdapter? = null
    private var cityId: Long? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val bundle: Bundle? = arguments
        cityId = if (bundle != null && bundle["cityId"] != null){
            bundle["cityId"] as Long
        } else {
            savedInstanceState?.getLong("cityId")
        }
        arguments?.clear()
        setHasOptionsMenu(true)
        submitToViewModel()
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_toolbar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val navController = findNavController()
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }

    private fun submitToViewModel(){
        viewModel.progressBarVisibility.observe(this, Observer {
            it?.let {
                if (!it){
                    pb_forecast.visibility = View.INVISIBLE
                }
            }
        })
        viewModel.forecastList.observe(this, Observer { it ->
            it?.let {
                adapter = ForecastAdapter(childFragmentManager, it)
                view_pager.adapter = adapter
                it.forEach {
                    if (it.cityId == cityId){
                        view_pager.currentItem = it.rowId
                        return@forEach
                    }
                }
            }
        })
    }

    override fun onStop() {
        super.onStop()
        cityId = adapter?.getCurrentPositionCityId(view_pager.currentItem)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        cityId?.let { outState.putLong("cityId", cityId!!) }
    }
}
