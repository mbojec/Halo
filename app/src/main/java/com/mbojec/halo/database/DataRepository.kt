package com.mbojec.halo.database

import android.location.Location
import androidx.lifecycle.LiveData
import com.mbojec.halo.AppExecutors
import javax.inject.Inject

class DataRepository @Inject constructor(private val database: Database, private val locationDao: LocationDao, private val appExecutors: AppExecutors) {

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

}
