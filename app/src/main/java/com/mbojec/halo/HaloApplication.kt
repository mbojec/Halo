package com.mbojec.halo

import android.app.Activity
import android.app.Application
import com.mbojec.halo.dagger.AppInjector
import com.mbojec.halo.utils.SharedPreferencesUtils
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import timber.log.Timber
import javax.inject.Inject

class HaloApplication : Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>
    @Inject lateinit var sharedPreferencesUtils: SharedPreferencesUtils

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        AppInjector.init(this)
    }

    override fun activityInjector() = dispatchingAndroidInjector
}