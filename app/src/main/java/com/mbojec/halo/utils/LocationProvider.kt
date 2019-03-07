package com.mbojec.halo.utils

import android.annotation.SuppressLint
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.mbojec.halo.Const.Companion.CURRENT_LOCATION_ID
import com.mbojec.halo.HaloApplication
import timber.log.Timber

object LocationProvider{

    @SuppressLint("MissingPermission")
    fun getCurrentLocation(application: HaloApplication, distanceLimit: Long, timeLimit: Long){
        val mFusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(application.applicationContext)
        if (PermissionUtils.checkIfPermissionGranted(application.applicationContext)) {
            mFusedLocationClient.locationAvailability.addOnSuccessListener { locationAvailability ->
                if (!locationAvailability.isLocationAvailable){
                    Timber.i("location is not available")
                } else {
                    Timber.i("location is available")
                    mFusedLocationClient.lastLocation.addOnSuccessListener { location ->
                        if (location != null) {
                            if (!checkIfLocationIsNear(location, application, distanceLimit)){
                                application.networkRepository.fetchCityData(location.longitude, location.latitude, true)
                                application.dataRepository.saveLocation(location, CURRENT_LOCATION_ID)
                                application.sharedPreferencesUtils.saveNewLocation(location)
                            } else if (checkIfUpdateNeeded(application, timeLimit)){
                                application.networkRepository.fetchCityData(location.longitude, location.latitude, true)
                                application.dataRepository.saveLocation(location, CURRENT_LOCATION_ID)
                                application.sharedPreferencesUtils.saveNewLocation(location)
                            }
                        } else {
                            Timber.i("location null")
                        }
                    }
                }
            }
        }
    }

    private fun checkIfLocationIsNear(newLocation: Location, application: HaloApplication, distanceLimit: Long): Boolean{
        val currentLocation = application.sharedPreferencesUtils.getCurrentLocation()?.split(",")
        val currentLatitude = currentLocation?.get(0)?.toDouble() ?: 1.0
        val currentLongitude = currentLocation?.get(1)?.toDouble() ?: 1.0
        val currentLocationObject = Location("currentLocation")
        currentLocationObject.latitude = currentLatitude
        currentLocationObject.longitude = currentLongitude
        val distance = currentLocationObject.distanceTo(newLocation)/1000
        return distance < distanceLimit
    }

    private fun checkIfUpdateNeeded(application: HaloApplication, timeLimit: Long): Boolean {
        val lastUpdateTime = application.sharedPreferencesUtils.getLatUpdateTime()
        val currentTime = DataUtils.getCurrentTime()
        return (currentTime - lastUpdateTime) > timeLimit
    }
}