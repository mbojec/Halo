package com.mbojec.halo.viewmodel

import androidx.lifecycle.ViewModel
import com.mbojec.halo.HaloApplication
import javax.inject.Inject

class ListViewModel @Inject constructor(haloApplication: HaloApplication) : ViewModel() {

    val forecastList = haloApplication.dataRepository.forecasts
}
