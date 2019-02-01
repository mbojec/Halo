package com.mbojec.halo

import android.widget.Toast
import com.mbojec.halo.model.DisposingObserver
import com.mbojec.halo.ui.MainActivity
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class NetworkRepository @Inject constructor(private val mapBoxApiClient: MapBoxApiClient){

    fun fetchCityList(searchCityName: String){
        val observable: Observable<CitySearchList> = mapBoxApiClient.getCitySearchList(searchCityName, "pl", "locality", "true")
        val observer: DisposingObserver<CitySearchList> = DisposingObserver()
        observer.onSubscribe(
            observable.subscribeOn(Schedulers.io())
                    .subscribe(
                        { it -> it?.let {} },
                        {throwable: Throwable ->  managingFailureResponse(throwable)},
                        {}
                    )
        )
    }

    private fun managingFailureResponse(throwable: Throwable) {
        if (throwable is IOException) {
            Timber.d(throwable, "network error")
        } else {
            Timber.d(throwable, "conversion error")
        }
    }
}