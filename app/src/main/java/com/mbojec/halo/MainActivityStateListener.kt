package com.mbojec.halo

import android.content.Context
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.mbojec.halo.background.WorkerManager
import com.mbojec.halo.model.SingletonHolder
import com.mbojec.halo.ui.MainActivity
import com.mbojec.halo.utils.*

class MainActivityStateListener(val application: HaloApplication, lifecycleOwner: LifecycleOwner, private val activity: MainActivity, private val firebaseRemoteConfig: FirebaseRemoteConfig) : DefaultLifecycleObserver {

    init { lifecycleOwner.lifecycle.addObserver(this) }

    companion object : SingletonHolder<MainActivityStateListener, HaloApplication, LifecycleOwner, MainActivity, FirebaseRemoteConfig>(::MainActivityStateListener)

    override fun onStart(owner: LifecycleOwner) {
        val updateArgData = FirebaseRemoteConfigUtils.fetchConfig(firebaseRemoteConfig, application as Context, activity)
        if (PermissionUtils.checkIfPermissionGranted(application.applicationContext)){
            val distanceLimit = updateArgData[Const.KEY_CURRENT_LOCATION_UPDATE_DISTANCE_LIMIT] as Long
            val timeLimit = updateArgData[Const.KEY_CURRENT_LOCATION_UPDATE_TIME_LIMIT] as Long
            LocationProvider.getCurrentLocation(application, distanceLimit, timeLimit)
        } else {
            PermissionUtils.requestPermissions(activity)
        }
        if (!NetworkUtils.isNetworkConnected(activity)) {
            NetworkUtils.showInfo(activity.findViewById(R.id.activity_main_layout))
        } else {
            val dataUpdateTimeLimit = updateArgData[Const.KEY_DATA_UPDATE_TIME_LIMIT] as Long
            WorkerManager.startSyncDataDownload(application, dataUpdateTimeLimit)
        }
    }

    override fun onStop(owner: LifecycleOwner) {
        MainActivityStateListener.clearInstance()
        DisposableManager.dispose()
    }
}