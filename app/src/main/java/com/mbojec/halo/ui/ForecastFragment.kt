package com.mbojec.halo.ui

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mbojec.halo.HaloApplication
import com.mbojec.halo.adapters.ShortTermForecastAdapter
import com.mbojec.halo.viewmodel.ForecastViewModel
import com.mbojec.halo.dagger.Injectable
import com.mbojec.halo.databinding.ForecastFragmentBinding
import javax.inject.Inject
import com.mbojec.halo.model.RecyclerViewConfiguration
import com.mbojec.halo.model.ShortTermForecast


class ForecastFragment : Fragment(), Injectable {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject lateinit var viewModel: ForecastViewModel
    @Inject lateinit var application: HaloApplication

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ForecastViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val arguments: Bundle? = arguments
        val cityId :Long = arguments?.getLong("CITY_ID")?:1
        viewModel.setId(cityId)
        val binding: ForecastFragmentBinding = DataBindingUtil.inflate(inflater,
            com.mbojec.halo.R.layout.forecast_fragment, container, false)
        binding.apply { viewModel = this@ForecastFragment.viewModel
        setLifecycleOwner (this@ForecastFragment)}
        return binding.root
    }
}
