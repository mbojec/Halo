package com.mbojec.halo.database

import android.location.Location
import androidx.lifecycle.LiveData
import com.mbojec.halo.AppExecutors
import com.mbojec.halo.model.Forecast
import com.mbojec.halo.model.ForecastListItem
import javax.inject.Inject

class DataRepository @Inject constructor(private val database: Database, private val locationDao: LocationDao, private val forecastDao: ForecastDao, private val forecastListDao: ForecastListDao, private val appExecutors: AppExecutors) {

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

    fun clearLocationData(){
        appExecutors.diskIO.execute{database.runInTransaction{
            run { locationDao.clearTable() }
        }}
    }



    val forecast: LiveData<ForecastEntity> = forecastDao.loadForecast()

    fun saveForecast(forecast: Forecast){
        appExecutors.diskIO.execute{database.runInTransaction{
            run {
                forecastDao.saveForecast(
                    ForecastEntity(
                        1,
                        forecast)
                )
            }
        }}

    }

    fun clearForecastData(){
        appExecutors.diskIO.execute{database.runInTransaction{
            run { forecastDao.clearTable() }
        }}
    }



    val forecastList: LiveData<ForecastListEntity> = forecastListDao.loadForecastList()

    fun saveForecastList(forecastList: List<ForecastListItem>){
        appExecutors.diskIO.execute { database.runInTransaction {
            run {
                forecastListDao.saveForecastList(
                    ForecastListEntity(
                        1,
                        forecastList
                    )
                )
            }
        } }
    }

    fun clearForecastList(){
        appExecutors.diskIO.execute { database.runInTransaction {
            run{forecastListDao.clearTable()}
        } }
    }


}
