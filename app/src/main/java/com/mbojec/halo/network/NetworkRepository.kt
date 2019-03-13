package com.mbojec.halo.network

import androidx.lifecycle.MutableLiveData
import com.mbojec.halo.BuildConfig
import com.mbojec.halo.HaloApplication
import com.mbojec.halo.database.entity.ForecastEntity
import com.mbojec.halo.model.SearchCityList
import com.mbojec.halo.model.DisposingObserver
import com.mbojec.halo.model.Forecast
import com.mbojec.halo.model.Response
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class NetworkRepository @Inject constructor(private val mapBoxApiClient: MapBoxApiClient, private val  darkSkyApiClient: DarkSkyApiClient, private val application: HaloApplication){

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

    fun fetchCityData(longitude: Double, latitude: Double, isCurrentLocation: Boolean){
        val observable: Observable<SearchCityList> = mapBoxApiClient.getCurrentCityData("$longitude", "$latitude", "pl", "place")
        val observer: DisposingObserver<SearchCityList> = DisposingObserver()
        observer.onSubscribe(
            observable.subscribeOn(Schedulers.io())
                .subscribe(
                    { searchCityList -> searchCityList?.let { searchCityList.features?.get(0)?.let { feature ->
                        fetchCityForecast(longitude, latitude, feature, getCityRowNumber(isCurrentLocation),getCityId(isCurrentLocation,it.features?.get(0)!!), isCurrentLocation)
                    } }},
                    {throwable: Throwable ->  managingFailureResponse(throwable)},
                    {}
                )
        )
    }

    private fun fetchCityForecast(longitude: Double, latitude: Double, feature: SearchCityList.Feature, rowId: Int, cityId: Long, isCurrentLocation: Boolean){
        val observable: Observable<Forecast> = darkSkyApiClient.getCityForecast(BuildConfig.DARK_SKY_API_KEY, "$latitude,$longitude", "pl", "si")
        val observer: DisposingObserver<Forecast> = DisposingObserver()
        observer.onSubscribe(
            observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { it -> it?.run {application.dataRepository.saveForecast(rowId ,cityId,feature, it)}
                    if (isCurrentLocation){
                        application.sharedPreferencesUtils.saveDataUpdateTime((it?.hourly?.dataHourlies?.get(0)?.time!!)* 1000)
                    }},
                    {throwable: Throwable ->  managingFailureResponse(throwable)},
                    {}
                )
        )
    }

    fun updateForecast(list: List<ForecastEntity>){
        val observableList: ArrayList<Observable<Forecast>> = ArrayList()
        val forecastList: ArrayList<Forecast> = ArrayList()
        list.forEach {
            val observable: Observable<Forecast> = darkSkyApiClient.getCityForecast(BuildConfig.DARK_SKY_API_KEY, "${it.feature.geometry!!.coordinates!![1]},${it.feature.geometry!!.coordinates!![0]}", "pl", "si")
            observableList.add(observable)
        }
        val mergeredObservable = Observable.merge(observableList)
        val observer: DisposingObserver<Forecast> = DisposingObserver()
        observer.onSubscribe(
            mergeredObservable.subscribeOn(Schedulers.io())
                .subscribe(
                    {it -> forecastList.add(it) },
                    {throwable: Throwable ->  managingFailureResponse(throwable)},
                    {updateData(list, forecastList)}
                ))
    }

    private fun updateData(list: List<ForecastEntity>, list2: ArrayList<Forecast>){
        for (i in list.indices) {
            list[i].forecast = list2[i]
        }
        application.dataRepository.forecastDao.updateData(list)
    }


    private fun getCityId(isCurrentLocation: Boolean, feature: SearchCityList.Feature): Long{
        return if (isCurrentLocation){
            1
        } else {
            val cityId = feature.feature_id?.split(".")?.get(1)
            cityId!!.toLong()
        }
    }

    private fun getCityRowNumber(isCurrentLocation: Boolean): Int{
        return if (isCurrentLocation){
            0
        } else {
            val numberOfRows = application.sharedPreferencesUtils.getNumberOfRows()
            application.sharedPreferencesUtils.saveCityListSize(numberOfRows + 1)
            numberOfRows + 1
        }
    }


    private fun managingFailureResponse(throwable: Throwable) {
        if (throwable is IOException) {
            Timber.d(throwable, "network error")
        } else {
            Timber.d(throwable, "conversion error")
        }
    }

}