package com.mbojec.halo.database

import android.location.Location
import androidx.lifecycle.LiveData
import com.mbojec.halo.AppExecutors
import com.mbojec.halo.model.SearchCityList
import com.mbojec.halo.database.dao.ForecastDao
import com.mbojec.halo.database.dao.LocationDao
import com.mbojec.halo.database.entity.ForecastEntity
import com.mbojec.halo.database.entity.LocationEntity
import com.mbojec.halo.model.Forecast
import javax.inject.Inject

class DataRepository @Inject constructor(private val database: Database, private val locationDao: LocationDao,  val forecastDao: ForecastDao, private val appExecutors: AppExecutors) {

    val location: LiveData<LocationEntity> = locationDao.loadCurrentLocation()

    fun saveLocation(location: Location, id: Int){
        appExecutors.diskIO.execute{database.runInTransaction{
            run {
                locationDao.insertLocation(
                    LocationEntity(
                        id,
                        location.longitude,
                        location.latitude,
                        System.currentTimeMillis()
                    )
                )
            }
        }}

    }

    val currentForecast: LiveData<ForecastEntity> = forecastDao.loadCurrentForecast()
    val forecast: LiveData<List<ForecastEntity>> = forecastDao.loadForecasts()
    val allForecasts: LiveData<List<ForecastEntity>> = forecastDao.loadAllForecasts()

    fun saveForecast(rowId: Int ,cityId: Long, feature: SearchCityList.Feature, forecast: Forecast){
        appExecutors.diskIO.execute{database.runInTransaction{
            run {
                forecastDao.saveForecast(
                    ForecastEntity(
                        rowId,
                        cityId,
                        feature,
                        forecast
                    )
                )
            }
        }}
    }

    fun updateList(forecastList: ArrayList<ForecastEntity>){
        appExecutors.diskIO.execute{database.runInTransaction{
            run {
                forecastDao.clearTable()
                forecastList.forEach { forecastDao.saveForecast(it)}
            }
        }}
    }

    fun loadForecast(cityId: Long): LiveData<ForecastEntity>{
        return forecastDao.loadForecast(cityId)
    }

}
