package com.mbojec.halo.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mbojec.halo.model.Forecast

class DataMinutelyConverter{

    private val gson = Gson()

    @TypeConverter
    fun stringToList(data: String?): List<Forecast.DataMinutely> {
        if (data == null) {
            return emptyList()
        }

        val listType = object : TypeToken<List<Forecast.DataMinutely>>() {

        }.type

        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun listToString(someObjects: List<Forecast.DataMinutely>): String {
        return gson.toJson(someObjects)
    }

}