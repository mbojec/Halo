package com.mbojec.halo.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mbojec.halo.database.converters.DataDailyConverter
import com.mbojec.halo.database.converters.DataHourlyConverter
import com.mbojec.halo.database.converters.DataMinutelyConverter
import com.mbojec.halo.database.converters.StringConverter
import com.mbojec.halo.database.dao.ForecastDao
import com.mbojec.halo.database.dao.LocationDao
import com.mbojec.halo.database.entity.ForecastEntity
import com.mbojec.halo.database.entity.LocationEntity

@Database(entities = [LocationEntity::class, ForecastEntity::class], version = 7)
@TypeConverters(StringConverter::class, DataDailyConverter::class, DataHourlyConverter::class, DataMinutelyConverter::class)
abstract class Database: RoomDatabase(){

    abstract fun locationDao(): LocationDao

    abstract fun forecastDao(): ForecastDao

}