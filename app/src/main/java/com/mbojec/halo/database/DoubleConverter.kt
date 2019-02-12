package com.mbojec.halo.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class DoubleConverter{

    private val gson = Gson()

    @TypeConverter
    fun stringToList(data: String?): List<Double> {
        if (data == null) {
            return emptyList()
        }

        val listType = object : TypeToken<List<Double>>() {

        }.type

        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun listToString(someObjects: List<Double>): String {
        return gson.toJson(someObjects)
    }

}