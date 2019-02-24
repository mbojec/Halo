package com.mbojec.halo.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mbojec.halo.database.entity.ForecastEntity

@Dao
interface ForecastDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveForecast(forecast: ForecastEntity)

    @Query("SELECT * FROM forecast WHERE cityId = 1")
    fun loadCurrentForecast(): LiveData<ForecastEntity>

    @Query("SELECT * FROM forecast WHERE cityId = :id")
    fun loadForecast(id: Long): LiveData<ForecastEntity>

    @Query("SELECT * FROM forecast  WHERE cityId != 1")
    fun loadForecasts(): LiveData<List<ForecastEntity>>

    @Query("SELECT * FROM forecast")
    fun loadAllForecasts(): LiveData<List<ForecastEntity>>

    @Transaction
    fun updateData(forecastList: List<ForecastEntity>) {
        clearTable()
        forecastList.forEach { saveForecast(it)}
    }

    @Query("DELETE FROM forecast")
    fun clearTable()
}