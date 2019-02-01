package com.mbojec.halo

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.mbojec.halo.model.SingletonHolder
import com.mbojec.halo.ui.MainActivity
import com.mbojec.halo.utils.DisposableManager
import com.mbojec.halo.utils.LocationProvider
import com.mbojec.halo.utils.NetworkUtils
import com.mbojec.halo.utils.PermissionUtils

class MainActivityStateListener(val application: HaloApplication, lifecycleOwner: LifecycleOwner, private val activity: MainActivity) : DefaultLifecycleObserver {

    init { lifecycleOwner.lifecycle.addObserver(this) }

    companion object : SingletonHolder<MainActivityStateListener, HaloApplication, LifecycleOwner, MainActivity>(::MainActivityStateListener)

    override fun onStart(owner: LifecycleOwner) {
        if (PermissionUtils.checkIfPermissionGranted(application.applicationContext)){
            LocationProvider.getCurrentLocation(application)
        } else {
            PermissionUtils.requestPermissions(activity)
        }
        if (!NetworkUtils.isNetworkConnected(activity)) {
            NetworkUtils.showInfo(activity.findViewById(R.id.activity_main_layout))
        }
    }

    override fun onStop(owner: LifecycleOwner) {
        MainActivityStateListener.clearInstance()
        DisposableManager.dispose()
    }
}