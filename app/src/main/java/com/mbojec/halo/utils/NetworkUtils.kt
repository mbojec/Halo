package com.mbojec.halo.utils

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.mbojec.halo.HaloApplication
import com.mbojec.halo.R

object NetworkUtils {
    fun isNetworkConnected(application: HaloApplication): Boolean {
        val connectivityManager = application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    fun isNetworkConnected(activity: Activity): Boolean {
        val connectivityManager = activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    fun showInfo(view: View){
        Snackbar.make(view,
            R.string.network_connection_failed,
            Snackbar.LENGTH_LONG).show()
    }
}