package com.mbojec.halo.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mbojec.halo.database.entity.ForecastListEntity

@Dao
interface ForecastListDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveForecastList(forecastList: ForecastListEntity)

    @Query("SELECT * FROM list")
    fun loadForecastList(): LiveData<List<ForecastListEntity>>

    @Query("DELETE FROM list")
    fun clearTable()

    @Transaction
    fun updateData(forecastList: List<ForecastListEntity>) {
        clearTable()
        forecastList.forEach { saveForecastList(it)}
    }

}