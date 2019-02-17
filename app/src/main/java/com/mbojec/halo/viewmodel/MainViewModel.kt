package com.mbojec.halo.viewmodel

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel;
import com.mbojec.halo.HaloApplication
import com.mbojec.halo.database.entity.ForecastEntity
import com.mbojec.halo.database.entity.ForecastListEntity
import javax.inject.Inject

class MainViewModel @Inject constructor(haloApplication: HaloApplication): ViewModel() {

    var forecastList: MediatorLiveData<List<ForecastEntity>> = MediatorLiveData()

    init {
        forecastList.addSource(haloApplication.dataRepository.forecasts){
            this.forecastList.postValue(it)
        }
    }
}
