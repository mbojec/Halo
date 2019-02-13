package com.mbojec.halo.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mbojec.halo.model.ForecastListItem

class ForecastListConverter{

    private val gson = Gson()

    @TypeConverter
    fun stringToList(data: String?): List<ForecastListItem> {
        if (data == null) {
            return emptyList()
        }

        val listType = object : TypeToken<List<ForecastListItem>>() {

        }.type

        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun listToString(someObjects: List<ForecastListItem>): String {
        return gson.toJson(someObjects)
    }

}