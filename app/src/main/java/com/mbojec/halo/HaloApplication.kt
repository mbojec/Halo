package com.mbojec.halo

import android.app.Application
import timber.log.Timber

class HaloApplication : Application(){

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}