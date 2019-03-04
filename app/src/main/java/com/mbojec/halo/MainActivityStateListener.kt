package com.mbojec.halo

import android.content.Context
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.mbojec.halo.model.SingletonHolder
import com.mbojec.halo.ui.MainActivity
import com.mbojec.halo.utils.*

class MainActivityStateListener(val application: HaloApplication, lifecycleOwner: LifecycleOwner, private val activity: MainActivity, private val firebaseRemoteConfig: FirebaseRemoteConfig) : DefaultLifecycleObserver {

    init { lifecycleOwner.lifecycle.addObserver(this) }

    companion object : SingletonHolder<MainActivityStateListener, HaloApplication, LifecycleOwner, MainActivity, FirebaseRemoteConfig>(::MainActivityStateListener)

    override fun onStart(owner: LifecycleOwner) {
        if (PermissionUtils.checkIfPermissionGranted(application.applicationContext)){
            LocationProvider.getCurrentLocation(application)
        } else {
            PermissionUtils.requestPermissions(activity)
        }
        if (!NetworkUtils.isNetworkConnected(activity)) {
            NetworkUtils.showInfo(activity.findViewById(R.id.activity_main_layout))
        } else {
            WorkerManager.startSyncDataDownload(application)
        }
        FirebaseRemoteConfigUtils.fetchConfig(firebaseRemoteConfig, application as Context, activity)
    }

    override fun onStop(owner: LifecycleOwner) {
        MainActivityStateListener.clearInstance()
        DisposableManager.dispose()
    }
}