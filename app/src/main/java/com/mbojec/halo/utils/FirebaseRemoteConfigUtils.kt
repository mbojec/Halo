package com.mbojec.halo.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.text.TextUtils
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.view.ContextThemeWrapper
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.mbojec.halo.BuildConfig
import com.mbojec.halo.Const.Companion.KEY_CURRENT_VERSION
import com.mbojec.halo.Const.Companion.KEY_UPDATE_REQUIRED
import com.mbojec.halo.Const.Companion.KEY_UPDATE_URL
import com.mbojec.halo.R
import com.mbojec.halo.ui.MainActivity
import timber.log.Timber

object FirebaseRemoteConfigUtils {
    fun setRemoteConfig(): FirebaseRemoteConfig {
        val firebaseRemoteConfig: FirebaseRemoteConfig = FirebaseRemoteConfig.getInstance()
        val firebaseRemoteConfigSettings = FirebaseRemoteConfigSettings.Builder().setDeveloperModeEnabled(BuildConfig.DEBUG).build()
        val remoteConfigDefaults = HashMap<String, Any>()
        remoteConfigDefaults[KEY_UPDATE_REQUIRED] = false
        remoteConfigDefaults[KEY_CURRENT_VERSION] = "1.0"
        remoteConfigDefaults[KEY_UPDATE_URL] = "https://play.google.com/store/apps/details?id=com.bojec.marek.weatherapp"

        firebaseRemoteConfig.setConfigSettings(firebaseRemoteConfigSettings)
        firebaseRemoteConfig.setDefaults(remoteConfigDefaults)
        return firebaseRemoteConfig
    }


    fun fetchConfig(firebaseRemoteConfig: FirebaseRemoteConfig, context: Context, activity: MainActivity){
        var cachexpiration: Long = 43200

        if (firebaseRemoteConfig.info.configSettings.isDeveloperModeEnabled){
            cachexpiration = 0
        }

        firebaseRemoteConfig.fetch(cachexpiration)
            .addOnSuccessListener {
                Timber.i( "remote config is fetched.")
                firebaseRemoteConfig.activateFetched()
            }
            .addOnFailureListener { e ->
                Timber.e(e)
            }
        ForceUpdateChecker(context, activity).check()
    }


    private class ForceUpdateChecker(private val context: Context,
                                     private val activity: MainActivity
    ) {
        fun check() {
            val remoteConfig = FirebaseRemoteConfig.getInstance()

            if (remoteConfig.getBoolean(KEY_UPDATE_REQUIRED)) {
                val currentVersion = remoteConfig.getString(KEY_CURRENT_VERSION)
                val appVersion = getAppVersion(context)
                val updateUrl = remoteConfig.getString(KEY_UPDATE_URL)
                if (TextUtils.equals(currentVersion, appVersion)) {
                    onUpdateNeeded(updateUrl)
                }
            }
        }

        @SuppressLint("TimberExceptionLogging")
        private fun getAppVersion(context: Context): String {
            var result = ""

            try {
                result = context.packageManager
                    .getPackageInfo(context.packageName, 0)
                    .versionName
                result = result.replace("[a-zA-Z]|-".toRegex(), "")
            } catch (e: PackageManager.NameNotFoundException) {
                Timber.e(e)
            }

            return result
        }

        private fun onUpdateNeeded(updateUrl: String) {
            val dialog: AlertDialog = AlertDialog.Builder(ContextThemeWrapper(context, R.style.DialogStyle))
                .setTitle(activity.getString(R.string.new_version_label))
                .setMessage(activity.getString(R.string.new_version_text))
                .setPositiveButton(activity.getString(R.string.new_version_positive_button)){ _, _ ->
                    redirectStore(updateUrl)}
                .setNegativeButton(activity.getString(R.string.new_version_negative_button)){ _, _ -> activity.finish()}
                .create()
            dialog.show()
        }

        private fun redirectStore(updateUrl: String) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(updateUrl))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            activity.startActivity(intent)
        }
    }
}