package com.mbojec.halo.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mbojec.halo.database.entity.LocationEntity

@Dao
interface LocationDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLocation(location: LocationEntity)

    @Query("SELECT * FROM location WHERE id = 1")
    fun loadCurrentLocation(): LiveData<LocationEntity>

    @Query("DELETE FROM location")
    fun clearTable()
}