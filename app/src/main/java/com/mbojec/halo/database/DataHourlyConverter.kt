package com.mbojec.halo.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mbojec.halo.model.Forecast

class DataHourlyConverter{

    private val gson = Gson()

    @TypeConverter
    fun stringToList(data: String?): List<Forecast.DataHourly> {
        if (data == null) {
            return emptyList()
        }

        val listType = object : TypeToken<List<Forecast.DataHourly>>() {

        }.type

        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun listToString(someObjects: List<Forecast.DataHourly>): String {
        return gson.toJson(someObjects)
    }

}