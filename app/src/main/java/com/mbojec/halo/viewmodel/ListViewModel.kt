package com.mbojec.halo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.mbojec.halo.HaloApplication
import com.mbojec.halo.adapters.ForecastListAdapter
import com.mbojec.halo.adapters.ShortTermForecastAdapter
import com.mbojec.halo.database.entity.ForecastEntity
import com.mbojec.halo.model.CurrentForecast
import com.mbojec.halo.model.RecyclerViewConfiguration
import com.mbojec.halo.utils.WeatherDataConverter
import java.util.ArrayList
import javax.inject.Inject

class ListViewModel @Inject constructor(haloApplication: HaloApplication) : ViewModel() {

    var mainForecast: LiveData<CurrentForecast> = object : LiveData<CurrentForecast>(){}
    private val currentForecast = haloApplication.dataRepository.currentForecast
    val forecastList = haloApplication.dataRepository.forecast

    init {
        mainForecast = Transformations.map(currentForecast){ it: ForecastEntity? ->
            it?.let {
                return@map WeatherDataConverter.createCurrentForecast(it, haloApplication) }
        }
    }
}
