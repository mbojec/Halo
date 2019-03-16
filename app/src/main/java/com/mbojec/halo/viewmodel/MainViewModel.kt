package com.mbojec.halo.viewmodel

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import com.mbojec.halo.HaloApplication
import com.mbojec.halo.database.entity.ForecastEntity
import javax.inject.Inject

class MainViewModel @Inject constructor(haloApplication: HaloApplication): ViewModel() {

    var forecastList: MediatorLiveData<List<ForecastEntity>> = MediatorLiveData()

    var progressBarVisibility: MutableLiveData<Boolean> = object : MutableLiveData<Boolean>(){}

    init {
        forecastList.addSource(haloApplication.dataRepository.allForecasts){
            it?.let {
                if (!it.isEmpty()){
                    progressBarVisibility.value = false
                }
                this.forecastList.postValue(it)
            }
        }
    }
}
