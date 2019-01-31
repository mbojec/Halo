package com.mbojec.halo.utils

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.mbojec.halo.Const.Companion.REQUEST_LOCATION_PERMISSIONS
import com.mbojec.halo.HaloApplication
import com.mbojec.halo.R
import com.mbojec.halo.ui.MainActivity
import java.util.ArrayList

object PermissionUtils {

    private val sPermissions = object : ArrayList<String>() {
        init {
            add(Manifest.permission.ACCESS_FINE_LOCATION)
            add(Manifest.permission.ACCESS_COARSE_LOCATION)
            add(Manifest.permission.ACCESS_NETWORK_STATE)
            add(Manifest.permission.INTERNET)
        }
    }

    fun requestPermissions(activity: MainActivity) {
        ActivityCompat.requestPermissions(activity, sPermissions.toTypedArray(), REQUEST_LOCATION_PERMISSIONS)
    }

    fun checkIfPermissionGranted(context: Context): Boolean {
        return checkAllPermissions(context)
    }

    private fun checkAllPermissions(context: Context): Boolean {
        var hasPermissions = true
        for (permission in sPermissions) {
            hasPermissions = hasPermissions and (ContextCompat.checkSelfPermission(
                context, permission) == PackageManager.PERMISSION_GRANTED)
        }
        return hasPermissions
    }

    fun requestPermissionResultSolution(grantResults: IntArray, activity: MainActivity, context: Context, application: Application, lifecycleOwner: LifecycleOwner){
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            LocationProvider.getCurrentLocation(application as HaloApplication)
        } else if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_DENIED){
            navigateToPermissionSettings(context, activity)
        }
    }

    private fun navigateToPermissionSettings(context: Context, activity: MainActivity){

        val dialog: AlertDialog = AlertDialog.Builder(ContextThemeWrapper(context, R.style.DialogStyle))
            .setMessage(context.getString(R.string.location_permission_denied_info))
            .setPositiveButton(context.getString(R.string.location_permission_denied_positive)){ _, _ ->
                redirectSettings(context, activity)
            }
            .setNegativeButton(context.getString(R.string.location_permission_denied_negative)){ _, _ -> activity.finish()}
            .create()
        dialog.show()
    }

    private fun redirectSettings(context: Context, activity: MainActivity){
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        val uri: Uri = Uri.fromParts("package", context.packageName, null)
        intent.data = uri
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        activity.startActivity(intent)
    }
}