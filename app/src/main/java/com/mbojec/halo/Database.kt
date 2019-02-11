package com.mbojec.halo

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [LocationEntity::class], version = 1)
abstract class Database: RoomDatabase(){

    abstract fun locationDao(): LocationDao

}