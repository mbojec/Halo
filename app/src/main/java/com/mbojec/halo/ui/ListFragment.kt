package com.mbojec.halo.ui

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.Handler
import android.view.*
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.gms.common.util.DataUtils
import com.mbojec.halo.adapters.ForecastListAdapter
import com.mbojec.halo.HaloApplication
import com.mbojec.halo.viewmodel.ListViewModel
import com.mbojec.halo.R
import com.mbojec.halo.dagger.Injectable
import com.mbojec.halo.utils.WeatherDataConverter
import kotlinx.android.synthetic.main.forecast_city_list_item.view.*
import kotlinx.android.synthetic.main.list_fragment.*
import javax.inject.Inject


class ListFragment : Fragment(), Injectable {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject lateinit var viewModel: ListViewModel
    @Inject lateinit var application: HaloApplication
    private lateinit var currentCityForecastLayout: CoordinatorLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ListViewModel::class.java)
    }

    override fun onResume() {
        super.onResume()
        floatingActionButton.setOnClickListener {
            val navController = findNavController()
            navController.navigate(R.id.search_dest)
        }
    }

    override fun onStart() {
        super.onStart()
        submitToVIewModel()
        ForecastListAdapter.getInstanceAndInit(this, viewModel, activity!!, application)
    }


    override fun onStop() {
        super.onStop()
        ForecastListAdapter.clearInstance()
    }

    private fun submitToVIewModel(){
        currentCityForecastLayout = view?.findViewById(R.id.currentCityForecastListItem)!!
        val currentCityCard = currentCityForecastLayout.findViewById<CardView>(R.id.city_card_view)
        val cityName = currentCityCard.findViewById<TextView>(R.id.tv_list_item_city_name)
        val cityTemp = currentCityCard.findViewById<TextView>(R.id.tv_list_item_temp)
        viewModel.currentForecast.observe(this, Observer {
            it?.let {forecastEntity ->
                currentCityCard.setCardBackgroundColor(WeatherDataConverter.getProperBackgroundColor(forecastEntity, application))
                cityName.text = forecastEntity.feature.textPl
                cityTemp.text = com.mbojec.halo.utils.DataUtils.formatTemperature(activity as Context, forecastEntity.forecast.currently?.temperature)
                Glide.with(this).load(com.mbojec.halo.utils.DataUtils.getImageResourceForWeatherCondition(forecastEntity.forecast.currently?.icon)).into(currentCityCard.iv_list_item_forecast)
                currentCityCard.setOnClickListener {
                    val cityId = forecastEntity.cityId
                    val action = ListFragmentDirections.actionListDestToMainDest(cityId)
                    val handler = Handler()
                    val task = Runnable {
                        it.findNavController().navigate(action)
                    }
                    handler.postDelayed(task, 500)
                }
            }
        })
    }
}
