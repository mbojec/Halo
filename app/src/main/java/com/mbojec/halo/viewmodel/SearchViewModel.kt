package com.mbojec.halo.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import com.mbojec.halo.HaloApplication
import com.mbojec.halo.SearchCityList
import com.mbojec.halo.model.Response
import javax.inject.Inject

class SearchViewModel @Inject constructor(val haloApplication: HaloApplication): ViewModel() {

    val responseStatus: MutableLiveData<Response> = MutableLiveData()
    val searchCityList: MutableLiveData<SearchCityList> = MutableLiveData()

    fun searchCity(cityName: String){
        haloApplication.networkRepository.fetchCityList(cityName, responseStatus, searchCityList)
    }

}
