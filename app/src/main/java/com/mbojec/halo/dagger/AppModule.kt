package com.mbojec.halo.dagger

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.mbojec.halo.AppExecutors
import com.mbojec.halo.HaloApplication
import com.mbojec.halo.utils.SharedPreferencesUtils
import dagger.Module
import dagger.Provides
import dagger.Reusable
import javax.inject.Singleton

@Module
class AppModule {
    @Provides
    @Singleton
    fun provideContext(app: Application): Context = app.applicationContext

    @Provides
    @Singleton
    fun provideBasicApp(app: Application): HaloApplication = app as HaloApplication

    @Provides
    fun provideAppExecutors(): AppExecutors = AppExecutors()

    @Provides
    @Reusable
    fun provideSharedPreferences(context: Context): SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    @Provides
    @Reusable
    fun provideSharedPreferencesEditor(sharedPreferences: SharedPreferences): SharedPreferences.Editor = sharedPreferences.edit()

    @Provides
    @Reusable
    fun provideSharedPreferencesUtils(app: Application, sharedPreferences: SharedPreferences, editor: SharedPreferences.Editor) =
        SharedPreferencesUtils(app as HaloApplication, sharedPreferences, editor)
}