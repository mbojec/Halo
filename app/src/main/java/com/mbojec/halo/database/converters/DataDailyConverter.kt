package com.mbojec.halo.database.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mbojec.halo.model.Forecast

class DataDailyConverter{

    private val gson = Gson()

    @TypeConverter
    fun stringToList(data: String?): List<Forecast.DataDaily> {
        if (data == null) {
            return emptyList()
        }

        val listType = object : TypeToken<List<Forecast.DataDaily>>() {

        }.type

        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun listToString(someObjects: List<Forecast.DataDaily>): String {
        return gson.toJson(someObjects)
    }

}