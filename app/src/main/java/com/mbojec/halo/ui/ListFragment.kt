package com.mbojec.halo.ui

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.*
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mbojec.halo.adapters.ForecastListAdapter
import com.mbojec.halo.HaloApplication
import com.mbojec.halo.viewmodel.ListViewModel
import com.mbojec.halo.R
import com.mbojec.halo.adapters.SimpleItemTouchHelperCallback
import com.mbojec.halo.dagger.Injectable
import com.mbojec.halo.database.entity.ForecastEntity
import com.mbojec.halo.databinding.ListFragmentBinding
import kotlinx.android.synthetic.main.list_fragment.*
import java.util.ArrayList
import javax.inject.Inject


class ListFragment : Fragment(), Injectable {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject lateinit var viewModel: ListViewModel
    @Inject lateinit var application: HaloApplication

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ListViewModel::class.java)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: ListFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.list_fragment, container, false)
        binding.apply { viewModel = this@ListFragment.viewModel
        setLifecycleOwner(this@ListFragment)}
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        submitToViewModel()
    }

    private fun submitToViewModel(){
        viewModel.forecastList.observe(this, Observer {
            it?.let { setAdapter(it as ArrayList<ForecastEntity>) }
        })
    }

    private fun setAdapter(list: ArrayList<ForecastEntity>){
        forecast_list.layoutManager = LinearLayoutManager(activity as Context, RecyclerView.VERTICAL, false)
        val forecastListAdapter = ForecastListAdapter(list, application)
        forecast_list.adapter = forecastListAdapter
        val touchHelper = ItemTouchHelper(SimpleItemTouchHelperCallback(forecastListAdapter))
        touchHelper.attachToRecyclerView(forecast_list)
    }
}
