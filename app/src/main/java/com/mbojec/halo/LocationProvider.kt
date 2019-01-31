package com.mbojec.halo

import android.annotation.SuppressLint
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.mbojec.halo.utils.PermissionUtils
import timber.log.Timber

object LocationProvider{

    @SuppressLint("MissingPermission")
    fun getCurrentLocation(application: HaloApplication){
        val mFusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(application.applicationContext)
        if (PermissionUtils.checkIfPermissionGranted(application.applicationContext)) {
            mFusedLocationClient.locationAvailability.addOnSuccessListener { locationAvailability ->
                if (!locationAvailability.isLocationAvailable){
                    Timber.i("location is not available")
                } else {
                    Timber.i("location is available")
                    mFusedLocationClient.lastLocation.addOnSuccessListener { location ->
                        if (location != null) {
                            Timber.i("location obtained")
                            Timber.i("location latitude: ${location.latitude}")
                            Timber.i("location longitude: ${location.longitude}")
                        } else {
                            Timber.i("location null")
                        }
                    }
                }
            }
        }
    }
}