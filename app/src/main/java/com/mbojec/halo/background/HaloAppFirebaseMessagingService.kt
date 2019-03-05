package com.mbojec.halo.background

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.mbojec.halo.R
import com.mbojec.halo.ui.MainActivity
import timber.log.Timber

class HaloAppFirebaseMessagingService: FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        Timber.d("From: %s", remoteMessage!!.from!!)
        if (remoteMessage.data.isNotEmpty()) {
            Timber.d("Message data payload: %s", remoteMessage.data)
        }

        if (remoteMessage.notification != null) {
            Timber.d("Message Notification Body: %s", remoteMessage.notification!!.body!!)
            sendNotification(remoteMessage.notification!!.body)
        }
    }

    override fun onNewToken(token: String?) {
        Timber.d("Refreshed token: %s", token!!)
    }

    private fun sendNotification(message: String?) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.action = Intent.ACTION_MAIN
        intent.addCategory(Intent.CATEGORY_LAUNCHER)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent,
            PendingIntent.FLAG_ONE_SHOT)

        val channelId = getString(R.string.system_notification_channel_id)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.clear_day)
            .setContentTitle(getString(R.string.system_notification_title))
            .setContentText(message)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.getNotificationChannel(channelId)
        }
        notificationManager.notify(0, notificationBuilder.build())
    }
}