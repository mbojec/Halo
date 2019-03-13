package com.mbojec.halo.utils

import android.content.SharedPreferences
import android.location.Location
import com.mbojec.halo.Const
import com.mbojec.halo.HaloApplication
import com.mbojec.halo.R
import javax.inject.Inject

class SharedPreferencesUtils @Inject constructor(private val haloApplication: HaloApplication, private val sharedPreferences: SharedPreferences, private val editor: SharedPreferences.Editor){

    fun saveCityListSize(listSize: Int){
        editor.putInt(haloApplication.applicationContext.getString(R.string.city_list_size_key), listSize)
        editor.apply()
    }

    fun saveNewLocation(newLocation: Location){
        editor.putString(Const.CURRENT_LOCATION, "${newLocation.latitude},${newLocation.longitude}")
        editor.apply()
    }

    fun saveDataUpdateTime(time: Long){
        editor.putLong(Const.DATA_SYNC_UPDATE_TIME, time)
        editor.apply()
    }

    fun getNumberOfRows(): Int = sharedPreferences.getInt(haloApplication.getString(R.string.city_list_size_key), 0)

    fun getCurrentLocation(): String? = sharedPreferences.getString(Const.CURRENT_LOCATION, "1.0,1.0")

    fun getDataUpdateTime(): Long = sharedPreferences.getLong(Const.DATA_SYNC_UPDATE_TIME, 1)
}
