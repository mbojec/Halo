package com.mbojec.halo.viewmodel

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel;
import com.mbojec.halo.HaloApplication
import com.mbojec.halo.database.ForecastEntity
import com.mbojec.halo.database.ForecastListEntity
import com.mbojec.halo.database.LocationEntity
import javax.inject.Inject

class MainViewModel @Inject constructor(haloApplication: HaloApplication): ViewModel() {

    var forecastList: MediatorLiveData<List<ForecastListEntity>> = MediatorLiveData()

    init {
        forecastList.addSource(haloApplication.dataRepository.forecastList){
            this.forecastList.postValue(it)
        }
    }
}
