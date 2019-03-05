package com.mbojec.halo.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.mbojec.halo.HaloApplication
import com.mbojec.halo.R
import timber.log.Timber

object NotificationUtils{

    fun setNotification(haloApplication: HaloApplication){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = haloApplication.getString(R.string.system_notification_channel_id)
            val channelName = haloApplication.getString(R.string.system_notification_channel_name)
            val notificationManager = haloApplication.getSystemService(NotificationManager::class.java)
            val notificationChannel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.enableLights(true)
            notificationChannel.enableVibration(true)
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    fun logUserTokenId() {
        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Timber.e(task.exception)
                    return@OnCompleteListener
                }
                val token = task.result?.token
                Timber.d("Current token: %s", token!!)
            })
    }

}