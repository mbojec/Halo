package com.mbojec.halo.utils

import android.annotation.SuppressLint
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.mbojec.halo.HaloApplication
import timber.log.Timber

object LocationProvider{

    @SuppressLint("MissingPermission")
    fun getCurrentLocation(application: HaloApplication): Location?{
        var currentLocation: Location? = null
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
                            currentLocation = location
                        } else {
                            Timber.i("location null")
                        }
                    }
                }
            }
        }
        return currentLocation
    }
}