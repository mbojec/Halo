package com.mbojec.halo.dagger

import android.content.Context
import androidx.room.Room
import com.mbojec.halo.AppExecutors
import com.mbojec.halo.database.*
import com.mbojec.halo.database.dao.ForecastDao
import com.mbojec.halo.database.dao.LocationDao
import dagger.Module
import dagger.Provides
import dagger.Reusable
import javax.inject.Singleton

@Module
class RoomModule {


    @Provides
    @Singleton
    fun provideAppDatabase(context: Context): Database =
        Room.databaseBuilder(context, Database::class.java, "halo-app-dp")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Reusable
    fun providesLocationDao(database: Database): LocationDao = database.locationDao()

    @Provides
    @Reusable
    fun providesForecastDao(database: Database): ForecastDao = database.forecastDao()


    @Provides
    @Singleton
    fun provideDataRepository(database: Database, locationDao: LocationDao, forecastDao: ForecastDao, appExecutors: AppExecutors): DataRepository =
        DataRepository(database, locationDao, forecastDao, appExecutors)


}