package com.mbojec.halo.viewmodel

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel;
import com.mbojec.halo.HaloApplication
import com.mbojec.halo.database.entity.ForecastEntity
import javax.inject.Inject

class MainViewModel @Inject constructor(haloApplication: HaloApplication): ViewModel() {

    var forecastList: MediatorLiveData<List<ForecastEntity>> = MediatorLiveData()

    init {
        forecastList.addSource(haloApplication.dataRepository.allForecasts){
            this.forecastList.postValue(it)
        }
    }
}
