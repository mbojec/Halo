package com.mbojec.halo.utils

import android.content.SharedPreferences
import com.mbojec.halo.HaloApplication
import com.mbojec.halo.R
import javax.inject.Inject

class SharedPreferencesUtils @Inject constructor(private val haloApplication: HaloApplication, private val sharedPreferences: SharedPreferences, private val editor: SharedPreferences.Editor){

    fun saveCityListSize(listSize: Int){
        editor.putInt(haloApplication.applicationContext.getString(R.string.city_list_size_key), listSize)
        editor.apply()
    }

    fun getNumberOfRows(): Int = sharedPreferences.getInt(haloApplication.getString(R.string.city_list_size_key), 0)

}
