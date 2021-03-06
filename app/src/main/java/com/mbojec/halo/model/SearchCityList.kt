package com.mbojec.halo.model

import androidx.room.Embedded
import androidx.room.TypeConverters
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.mbojec.halo.database.converters.ContextConverter
import com.mbojec.halo.database.converters.DoubleConverter
import com.mbojec.halo.database.converters.StringConverter

class SearchCityList {
    @SerializedName("type")
    @Expose
    var type: String? = null
//
//    @SerializedName("query")
//    @Expose
//    var query: List<String>? = null

    @SerializedName("features")
    @Expose
    var features: List<Feature>? = null

    @SerializedName("attribution")
    @Expose
    var attribution: String? = null

    class Feature {

        @SerializedName("id")
        @Expose
        var feature_id: String? = null

        @SerializedName("type")
        @Expose
        var type: String? = null

        @SerializedName("place_type")
        @Expose
        @TypeConverters(StringConverter::class)
        var placeType: List<String>? = null

        @SerializedName("relevance")
        @Expose
        var relevance: Double? = null

        @SerializedName("properties")
        @Expose
        @Embedded
        var properties: Properties? = null

        @SerializedName("text_pl")
        @Expose
        var textPl: String? = null

        @SerializedName("place_name_pl")
        @Expose
        var placeNamePl: String? = null

        @SerializedName("text")
        @Expose
        var text: String? = null

        @SerializedName("place_name")
        @Expose
        var placeName: String? = null

        @SerializedName("bbox")
        @Expose
        @TypeConverters(DoubleConverter::class)
        var bbox: List<Double>? = null

        @SerializedName("center")
        @Expose
        @TypeConverters(DoubleConverter::class)
        var center: List<Double>? = null

        @SerializedName("geometry")
        @Expose
        @Embedded(prefix = "geometry")
        var geometry: Geometry? = null

        @SerializedName("context")
        @Expose
        @TypeConverters(ContextConverter::class)
        var context: List<Context>? = null

        @SerializedName("language_pl")
        @Expose
        var languagePl: String? = null

        @SerializedName("language")
        @Expose
        var language: String? = null

        class Geometry {

            @SerializedName("type")
            @Expose
            var type: String? = null

            @SerializedName("coordinates")
            @Expose
            @TypeConverters(DoubleConverter::class)
            var coordinates: List<Double>? = null

        }

        class Properties {

            @SerializedName("wikidata")
            @Expose
            var wikidata: String? = null

        }

        class Context {

            @SerializedName("id")
            @Expose
            var id: String? = null

            @SerializedName("text_pl")
            @Expose
            var textPl: String? = null

            @SerializedName("text")
            @Expose
            var text: String? = null

            @SerializedName("wikidata")
            @Expose
            var wikidata: String? = null

            @SerializedName("language_pl")
            @Expose
            var languagePl: String? = null

            @SerializedName("language")
            @Expose
            var language: String? = null

            @SerializedName("short_code")
            @Expose
            var shortCode: String? = null

        }

    }

}
