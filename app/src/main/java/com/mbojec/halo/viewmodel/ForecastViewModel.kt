package com.mbojec.halo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel;
import com.mbojec.halo.HaloApplication
import com.mbojec.halo.database.entity.ForecastEntity
import javax.inject.Inject

class ForecastViewModel @Inject constructor(haloApplication: HaloApplication): ViewModel() {

    private val cityId: MutableLiveData<Long> = MutableLiveData()
    var forecast: LiveData<ForecastEntity> = object : LiveData<ForecastEntity>(){}

    fun setId(cityId: Long) {
        this.cityId.value = cityId
    }

    init {
        forecast = Transformations.switchMap(cityId) { id -> haloApplication.dataRepository.loadForecast(id)}
    }
}
