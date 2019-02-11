package com.mbojec.halo.dagger

import android.content.Context
import androidx.room.Room
import com.mbojec.halo.AppExecutors
import com.mbojec.halo.database.DataRepository
import com.mbojec.halo.database.Database
import com.mbojec.halo.database.LocationDao
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
            .build()

    @Provides
    @Reusable
    fun providesLocationDao(database: Database): LocationDao = database.locationDao()

    @Provides
    @Singleton
    fun provideDataRepository(database: Database, locationDao: LocationDao, appExecutors: AppExecutors): DataRepository =
        DataRepository(database, locationDao, appExecutors)


}