package com.mbojec.halo.ui

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mbojec.halo.HaloApplication
import com.mbojec.halo.viewmodel.ForecastViewModel
import com.mbojec.halo.R
import com.mbojec.halo.adapters.LongTermForecastAdapter
import com.mbojec.halo.adapters.ShortTermForecastAdapter
import com.mbojec.halo.dagger.Injectable
import com.mbojec.halo.database.entity.ForecastEntity
import kotlinx.android.synthetic.main.forecast_fragment.*
import javax.inject.Inject


class ForecastFragment : Fragment(), Injectable {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject lateinit var viewModel: ForecastViewModel
    @Inject lateinit var application: HaloApplication
    private lateinit var forecastEntity: ForecastEntity

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ForecastViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val arguments: Bundle? = arguments
        val cityId :Long = arguments?.getLong("CITY_ID")?:1
        viewModel.setId(cityId)
        submitToViewModel()
        return inflater.inflate(R.layout.forecast_fragment, container, false)
    }

    private fun submitToViewModel(){
        viewModel.forecast.observe(this, Observer {
            it?.let { text_view.text = it.feature.placeName
                forecastEntity = it
                createAdapter()}
        })
    }

    private fun createAdapter(){
        val longTermAdapter = LongTermForecastAdapter(forecastEntity.forecast.daily!!.dataDailies!!, application)
        val shortTermAdapter = ShortTermForecastAdapter(forecastEntity.forecast.hourly!!.dataHourlies!!, application)
        val layoutManager = LinearLayoutManager(activity as Context, RecyclerView.HORIZONTAL, false)
        val layoutManager2 = LinearLayoutManager(activity as Context, RecyclerView.HORIZONTAL, false)
        longTermForecastRecyclerView.layoutManager = layoutManager
        longTermForecastRecyclerView.adapter = longTermAdapter
        shortTermForecastRecyclerView.layoutManager = layoutManager2
        shortTermForecastRecyclerView.adapter = shortTermAdapter
    }
}
