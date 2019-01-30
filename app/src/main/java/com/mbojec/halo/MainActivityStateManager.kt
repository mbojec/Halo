package com.mbojec.halo

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.mbojec.halo.ui.MainActivity

class MainActivityStateManager{

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var sInstance: MainActivityStateListener? = null

        fun bindMainActivityStateListener(application: HaloApplication, lifecycleOwner: LifecycleOwner, context: Context, activity: MainActivity) {
            getInstance(application, lifecycleOwner, context, activity)
        }

        fun unBindMainActivityStateListener() {
            sInstance = null
        }

        private fun getInstance(application: HaloApplication, lifecycleOwner: LifecycleOwner, context: Context, activity: MainActivity): MainActivityStateListener {
            return sInstance ?: synchronized(this) {
                sInstance ?: MainActivityStateListener(application, lifecycleOwner, context, activity)
            }.also { sInstance = it }
        }
    }



    private class MainActivityStateListener(private val application: HaloApplication, private val lifecycleOwner: LifecycleOwner, private val context: Context, private val activity: MainActivity) :
        DefaultLifecycleObserver {

        init { lifecycleOwner.lifecycle.addObserver(this) }


        override fun onStart(owner: LifecycleOwner) {
            if (android.os.Build.VERSION.SDK_INT >= 23){
                if (PermissionUtils.checkIfPermissionGranted(context)){
                    return
                } else {
                    PermissionUtils.requestPermissions(activity)
                }
            }
            if (!NetworkUtils.isNetworkConnected(activity)) {
                NetworkUtils.showInfo(activity.findViewById(R.id.activity_main_layout))
            }
        }

        override fun onDestroy(owner: LifecycleOwner) {
            unBindMainActivityStateListener()
        }
    }
}