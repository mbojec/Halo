package com.mbojec.halo.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mbojec.halo.SearchCityList

class ContextConverter{

    private val gson = Gson()

    @TypeConverter
    fun stringToList(data: String?): List<SearchCityList.Feature.Context> {
        if (data == null) {
            return emptyList()
        }

        val listType = object : TypeToken<List<SearchCityList.Feature.Context>>() {

        }.type

        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun listToString(someObjects: List<SearchCityList.Feature.Context>): String {
        return gson.toJson(someObjects)
    }

}