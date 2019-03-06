package com.mbojec.halo.ui

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
//    private var recyclerView: RecyclerView? = null

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
        submitToViewModel()
        return binding.root
    }

    private fun submitToViewModel(){
        viewModel.mainForecast.observe(this, Observer {
            it?.let { setMainForecast(it) }
        })

        viewModel.shortTermForecast.observe(this, Observer {
            it?.let { setShortTermForecast(it) }
        })

        viewModel.longTermForecastList.observe(this, Observer {
            it?.let { setLongTermForecast(it) }
        })

    }

    private fun setMainForecast(mainForecast: CurrentForecast){
        val toolbarLayout = in_toolbar as CoordinatorLayout
        val toolbar = toolbarLayout.toolbar_forecast as Toolbar
        toolbar.title = mainForecast.cityName
        if(mainForecast.id <= 1){
            toolbar.setNavigationIcon(R.drawable.ic_location)
        }
    }

    private fun setShortTermForecast(shortTermForecast: List<ShortTermForecast>){
        val shortTermLayout = in_short_term_forecast as CoordinatorLayout
        val adapter = ShortTermForecastAdapter(shortTermForecast, application)
        shortTermLayout.rv_short_term_forecast.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        shortTermLayout.rv_short_term_forecast.adapter = adapter
    }

    private fun setLongTermForecast(longTermForecast: List<LongTermForecast>){
        val longTermForecastLayout = in_long_term_forecast as ConstraintLayout

        longTermForecastLayout.tv_2day_date.text = longTermForecast[1].dayName
        longTermForecastLayout.tv_3day_date.text = longTermForecast[2].dayName
        longTermForecastLayout.tv_4day_date.text = longTermForecast[3].dayName
        longTermForecastLayout.tv_5day_date.text = longTermForecast[4].dayName
        longTermForecastLayout.tv_6day_date.text = longTermForecast[5].dayName
        longTermForecastLayout.tv_7day_date.text = longTermForecast[6].dayName

        longTermForecastLayout.tv_2day_temp_day.text = longTermForecast[1].dayTemp
        longTermForecastLayout.tv_3day_temp_day.text = longTermForecast[2].dayTemp
        longTermForecastLayout.tv_4day_temp_day.text = longTermForecast[3].dayTemp
        longTermForecastLayout.tv_5day_temp_day.text = longTermForecast[4].dayTemp
        longTermForecastLayout.tv_6day_temp_day.text = longTermForecast[5].dayTemp
        longTermForecastLayout.tv_7day_temp_day.text = longTermForecast[6].dayTemp

        longTermForecastLayout.tv_2day_temp_night.text = longTermForecast[1].nightTemp
        longTermForecastLayout.tv_3day_temp_night.text = longTermForecast[2].nightTemp
        longTermForecastLayout.tv_4day_temp_night.text = longTermForecast[3].nightTemp
        longTermForecastLayout.tv_5day_temp_night.text = longTermForecast[4].nightTemp
        longTermForecastLayout.tv_6day_temp_night.text = longTermForecast[5].nightTemp
        longTermForecastLayout.tv_7day_temp_night.text = longTermForecast[6].nightTemp

        Glide.with(this).load(longTermForecast[1].weatherImage).into(longTermForecastLayout.iv_2day_forecast_image)
        Glide.with(this).load(longTermForecast[2].weatherImage).into(longTermForecastLayout.iv_3day_forecast_image)
        Glide.with(this).load(longTermForecast[3].weatherImage).into(longTermForecastLayout.iv_4day_forecast_image)
        Glide.with(this).load(longTermForecast[4].weatherImage).into(longTermForecastLayout.iv_5day_forecast_image)
        Glide.with(this).load(longTermForecast[5].weatherImage).into(longTermForecastLayout.iv_6day_forecast_image)
        Glide.with(this).load(longTermForecast[6].weatherImage).into(longTermForecastLayout.iv_7day_forecast_image)


    }
    
}
