package com.mbojec.halo

import androidx.lifecycle.MutableLiveData
import com.mbojec.halo.model.DisposingObserver
import com.mbojec.halo.model.Response
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class NetworkRepository @Inject constructor(private val mapBoxApiClient: MapBoxApiClient){

    fun fetchCityList(searchCityName: String, responseStatus: MutableLiveData<Response>, searchCityList: MutableLiveData<SearchCityList>){
        responseStatus.postValue(Response.loading())
        val observable: Observable<SearchCityList> = mapBoxApiClient.getCitySearchList(searchCityName, "pl", "place", "true", "10")
        val observer: DisposingObserver<SearchCityList> = DisposingObserver()
        observer.onSubscribe(
            observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { responseStatus.postValue(Response.loading()) }
                .subscribe(
                    { it -> it?.let {searchCityList.postValue(it)}.also { responseStatus.postValue(Response.success())} },
                    {throwable: Throwable ->  managingFailureResponse(throwable).also { responseStatus.postValue(Response.error(throwable)) }},
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