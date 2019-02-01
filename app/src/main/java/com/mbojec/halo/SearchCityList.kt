package com.mbojec.halo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SearchCityList {
    @SerializedName("type")
    @Expose
    var type: String? = null
    @SerializedName("query")
    @Expose
    var query: List<String>? = null
    @SerializedName("features")
    @Expose
    var features: List<Feature>? = null
    @SerializedName("attribution")
    @Expose
    var attribution: String? = null

    inner class Feature {

        @SerializedName("id")
        @Expose
        var id: String? = null
        @SerializedName("type")
        @Expose
        var type: String? = null
        @SerializedName("place_type")
        @Expose
        var placeType: List<String>? = null
        @SerializedName("relevance")
        @Expose
        var relevance: Double? = null
        @SerializedName("properties")
        @Expose
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
        var bbox: List<Double>? = null
        @SerializedName("center")
        @Expose
        var center: List<Double>? = null
        @SerializedName("geometry")
        @Expose
        var geometry: Geometry? = null
        @SerializedName("context")
        @Expose
        var context: List<Context>? = null
        @SerializedName("language_pl")
        @Expose
        var languagePl: String? = null
        @SerializedName("language")
        @Expose
        var language: String? = null

        inner class Geometry {

            @SerializedName("type")
            @Expose
            var type: String? = null
            @SerializedName("coordinates")
            @Expose
            var coordinates: List<Double>? = null

        }

        inner class Properties {

            @SerializedName("wikidata")
            @Expose
            var wikidata: String? = null

        }

        inner class Context {

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
