package com.mbojec.halo.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ForecastDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveForecast(forecast: ForecastEntity)

    @Query("SELECT * FROM forecast WHERE cityId = 1")
    fun loadSimpleForecast(): LiveData<ForecastEntity>

    @Query("SELECT * FROM forecast WHERE cityId = :id")
    fun loadForecast(id: Int): LiveData<ForecastEntity>

    @Query("SELECT * FROM forecast")
    fun loadAllForecasts(): LiveData<List<ForecastEntity>>

    @Query("DELETE FROM forecast")
    fun clearTable()
}