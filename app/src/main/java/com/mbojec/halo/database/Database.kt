package com.mbojec.halo.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [LocationEntity::class, ForecastEntity::class], version = 2)
@TypeConverters(StringConverter::class, DataDailyConverter::class, DataHourlyConverter::class, DataMinutelyConverter::class)
abstract class Database: RoomDatabase(){

    abstract fun locationDao(): LocationDao

    abstract fun forecastDao(): ForecastDao

}