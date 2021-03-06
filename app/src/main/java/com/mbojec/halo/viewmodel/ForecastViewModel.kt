package com.mbojec.halo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mbojec.halo.HaloApplication
import com.mbojec.halo.adapters.ShortTermForecastAdapter
import com.mbojec.halo.database.entity.ForecastEntity
import com.mbojec.halo.model.*
import com.mbojec.halo.utils.WeatherDataConverter
import javax.inject.Inject

class ForecastViewModel @Inject constructor(val haloApplication: HaloApplication): ViewModel() {

    private val cityId: MutableLiveData<Long> = MutableLiveData()

    fun setId(cityId: Long) {
        this.cityId.value = cityId
    }

    var mainForecast: LiveData<CurrentForecast> = object : LiveData<CurrentForecast>(){}

    var shortTermForecast: LiveData<List<ShortTermForecast>> = object: LiveData<List<ShortTermForecast>>(){}

    var longTermForecastList: LiveData<List<LongTermForecast>> = object: LiveData<List<LongTermForecast>>(){}

    var forecastInfo: LiveData<ForecastInfo> = object : LiveData<ForecastInfo>() {}

    private val forecastEntity = Transformations.switchMap(cityId) { id -> haloApplication.dataRepository.loadForecast(id)}

    init {
        mainForecast = Transformations.map(forecastEntity){ it: ForecastEntity? ->
            it?.let { return@map WeatherDataConverter.createCurrentForecast(it, haloApplication) }
        }

        shortTermForecast = Transformations.map(forecastEntity){it: ForecastEntity? ->
            it?.let {return@map WeatherDataConverter.createShortTermForecastList(it, haloApplication)}
        }

        longTermForecastList = Transformations.map(forecastEntity){ it: ForecastEntity? ->
            it?.let {return@map WeatherDataConverter.createLongTermForecastList(it, haloApplication)}
        }

        forecastInfo = Transformations.map(forecastEntity){it: ForecastEntity? ->
            it?.let {return@map WeatherDataConverter.createForecastInfo(it, haloApplication)}
        }
    }


}
