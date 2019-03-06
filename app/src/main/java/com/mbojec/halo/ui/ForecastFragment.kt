package com.mbojec.halo.ui

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mbojec.halo.HaloApplication
import com.mbojec.halo.viewmodel.ForecastViewModel
import com.mbojec.halo.R
import com.mbojec.halo.adapters.ShortTermForecastAdapter
import com.mbojec.halo.dagger.Injectable
import com.mbojec.halo.databinding.ForecastFragmentBinding
import com.mbojec.halo.model.CurrentForecast
import com.mbojec.halo.model.ForecastInfo
import com.mbojec.halo.model.LongTermForecast
import com.mbojec.halo.model.ShortTermForecast
import kotlinx.android.synthetic.main.additional_forecast_info.view.*
import kotlinx.android.synthetic.main.background_view.view.*
import kotlinx.android.synthetic.main.forecast_fragment.*
import kotlinx.android.synthetic.main.long_term_forecast.view.*
import kotlinx.android.synthetic.main.main_forecast.view.*
import kotlinx.android.synthetic.main.short_term_forecast.view.*
import javax.inject.Inject


class ForecastFragment : Fragment(), Injectable {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject lateinit var viewModel: ForecastViewModel
    @Inject lateinit var application: HaloApplication
    private var recyclerView: RecyclerView? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ForecastViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val arguments: Bundle? = arguments
        val cityId :Long = arguments?.getLong("CITY_ID")?:1
        viewModel.setId(cityId)
        val binding: ForecastFragmentBinding = DataBindingUtil.inflate(inflater,R.layout.forecast_fragment, container, false)
        binding.apply { viewModel = this@ForecastFragment.viewModel
        setLifecycleOwner (this@ForecastFragment)}
        recyclerView = binding.inShortTermForecast.rv_short_term_forecast
        submitToViewModel()
        return binding.root
    }

    private fun submitToViewModel(){
        viewModel.shortTermForecast.observe(this, Observer {
            it?.let { setShortTermForecast(it) }
        })

    }

    private fun setShortTermForecast(shortTermForecast: List<ShortTermForecast>){
        recyclerView!!.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        val adapter = ShortTermForecastAdapter(shortTermForecast, application)
        recyclerView!!.adapter = adapter
    }

}
