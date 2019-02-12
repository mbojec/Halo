package com.mbojec.halo.viewmodel

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.mbojec.halo.HaloApplication
import com.mbojec.halo.database.ForecastEntity
import com.mbojec.halo.database.LocationEntity
import javax.inject.Inject

class ActivityViewModel @Inject constructor(haloApplication: HaloApplication): ViewModel(){

    var location: MediatorLiveData<LocationEntity> = MediatorLiveData()
    var forecast: MediatorLiveData<ForecastEntity> = MediatorLiveData()

    init {
        location.addSource(haloApplication.dataRepository.location){
            this.location.postValue(it)
        }
        forecast.addSource(haloApplication.dataRepository.forecast){
            this.forecast.postValue(it)
        }
    }

}