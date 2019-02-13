package com.mbojec.halo.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ForecastListDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveForecastList(forecastList: ForecastListEntity)

    @Query("SELECT * FROM list")
    fun loadForecastList(): LiveData<ForecastListEntity>

    @Query("DELETE FROM list")
    fun clearTable()
}