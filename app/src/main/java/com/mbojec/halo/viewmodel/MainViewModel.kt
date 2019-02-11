package com.mbojec.halo.viewmodel

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel;
import com.mbojec.halo.HaloApplication
import com.mbojec.halo.database.LocationEntity
import javax.inject.Inject

class MainViewModel @Inject constructor(haloApplication: HaloApplication): ViewModel() {

    var location: MediatorLiveData<LocationEntity> = MediatorLiveData()

    init {
        location.addSource(haloApplication.dataRepository.location){
            this.location.postValue(it)
        }
    }
}
