package com.mbojec.halo.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Forecast {

    @SerializedName("latitude")
    @Expose
    var latitude: Double? = null
    @SerializedName("longitude")
    @Expose
    var longitude: Double? = null
    @SerializedName("timezone")
    @Expose
    var timezone: String? = null
    @SerializedName("currently")
    @Expose
    var currently: Currently? = null
    @SerializedName("minutely")
    @Expose
    var minutely: Minutely? = null
    @SerializedName("hourly")
    @Expose
    var hourly: Hourly? = null
    @SerializedName("daily")
    @Expose
    var daily: Daily? = null
    @SerializedName("flags")
    @Expose
    var flags: Flags? = null
    @SerializedName("offset")
    @Expose
    var offset: Int? = null

    inner class Currently {

        @SerializedName("time")
        @Expose
        var time: Int? = null
        @SerializedName("summary")
        @Expose
        var summary: String? = null
        @SerializedName("icon")
        @Expose
        var icon: String? = null
        @SerializedName("nearestStormDistance")
        @Expose
        var nearestStormDistance: Int? = null
        @SerializedName("nearestStormBearing")
        @Expose
        var nearestStormBearing: Int? = null
        @SerializedName("precipIntensity")
        @Expose
        var precipIntensity: Int? = null
        @SerializedName("precipProbability")
        @Expose
        var precipProbability: Int? = null
        @SerializedName("temperature")
        @Expose
        var temperature: Double? = null
        @SerializedName("apparentTemperature")
        @Expose
        var apparentTemperature: Double? = null
        @SerializedName("dewPoint")
        @Expose
        var dewPoint: Double? = null
        @SerializedName("humidity")
        @Expose
        var humidity: Double? = null
        @SerializedName("pressure")
        @Expose
        var pressure: Double? = null
        @SerializedName("windSpeed")
        @Expose
        var windSpeed: Double? = null
        @SerializedName("windGust")
        @Expose
        var windGust: Double? = null
        @SerializedName("windBearing")
        @Expose
        var windBearing: Int? = null
        @SerializedName("cloudCover")
        @Expose
        var cloudCover: Double? = null
        @SerializedName("uvIndex")
        @Expose
        var uvIndex: Int? = null
        @SerializedName("visibility")
        @Expose
        var visibility: Int? = null
        @SerializedName("ozone")
        @Expose
        var ozone: Double? = null

    }


    inner class Daily {

        @SerializedName("summary")
        @Expose
        var summary: String? = null
        @SerializedName("icon")
        @Expose
        var icon: String? = null
        @SerializedName("data")
        @Expose
        var dataDailies: List<DataDaily>? = null

    }

    inner class DataMinutely {

        @SerializedName("time")
        @Expose
        var time: Int? = null
        @SerializedName("precipIntensity")
        @Expose
        var precipIntensity: Double? = null
        @SerializedName("precipProbability")
        @Expose
        var precipProbability: Double? = null
        @SerializedName("precipIntensityError")
        @Expose
        var precipIntensityError: Double? = null
        @SerializedName("precipType")
        @Expose
        var precipType: String? = null

    }


    inner class DataHourly {

        @SerializedName("time")
        @Expose
        var time: Int? = null
        @SerializedName("summary")
        @Expose
        var summary: String? = null
        @SerializedName("icon")
        @Expose
        var icon: String? = null
        @SerializedName("precipIntensity")
        @Expose
        var precipIntensity: Double? = null
        @SerializedName("precipProbability")
        @Expose
        var precipProbability: Double? = null
        @SerializedName("temperature")
        @Expose
        var temperature: Double? = null
        @SerializedName("apparentTemperature")
        @Expose
        var apparentTemperature: Double? = null
        @SerializedName("dewPoint")
        @Expose
        var dewPoint: Double? = null
        @SerializedName("humidity")
        @Expose
        var humidity: Double? = null
        @SerializedName("pressure")
        @Expose
        var pressure: Double? = null
        @SerializedName("windSpeed")
        @Expose
        var windSpeed: Double? = null
        @SerializedName("windGust")
        @Expose
        var windGust: Double? = null
        @SerializedName("windBearing")
        @Expose
        var windBearing: Int? = null
        @SerializedName("cloudCover")
        @Expose
        var cloudCover: Int? = null
        @SerializedName("uvIndex")
        @Expose
        var uvIndex: Int? = null
        @SerializedName("visibility")
        @Expose
        var visibility: Double? = null
        @SerializedName("ozone")
        @Expose
        var ozone: Double? = null
        @SerializedName("precipType")
        @Expose
        var precipType: String? = null

    }


    inner class DataDaily {

        @SerializedName("time")
        @Expose
        var time: Int? = null
        @SerializedName("summary")
        @Expose
        var summary: String? = null
        @SerializedName("icon")
        @Expose
        var icon: String? = null
        @SerializedName("sunriseTime")
        @Expose
        var sunriseTime: Int? = null
        @SerializedName("sunsetTime")
        @Expose
        var sunsetTime: Int? = null
        @SerializedName("moonPhase")
        @Expose
        var moonPhase: Double? = null
        @SerializedName("precipIntensity")
        @Expose
        var precipIntensity: Int? = null
        @SerializedName("precipIntensityMax")
        @Expose
        var precipIntensityMax: Double? = null
        @SerializedName("precipIntensityMaxTime")
        @Expose
        var precipIntensityMaxTime: Int? = null
        @SerializedName("precipProbability")
        @Expose
        var precipProbability: Int? = null
        @SerializedName("precipType")
        @Expose
        var precipType: String? = null
        @SerializedName("temperatureHigh")
        @Expose
        var temperatureHigh: Double? = null
        @SerializedName("temperatureHighTime")
        @Expose
        var temperatureHighTime: Int? = null
        @SerializedName("temperatureLow")
        @Expose
        var temperatureLow: Double? = null
        @SerializedName("temperatureLowTime")
        @Expose
        var temperatureLowTime: Int? = null
        @SerializedName("apparentTemperatureHigh")
        @Expose
        var apparentTemperatureHigh: Double? = null
        @SerializedName("apparentTemperatureHighTime")
        @Expose
        var apparentTemperatureHighTime: Int? = null
        @SerializedName("apparentTemperatureLow")
        @Expose
        var apparentTemperatureLow: Double? = null
        @SerializedName("apparentTemperatureLowTime")
        @Expose
        var apparentTemperatureLowTime: Int? = null
        @SerializedName("dewPoint")
        @Expose
        var dewPoint: Double? = null
        @SerializedName("humidity")
        @Expose
        var humidity: Double? = null
        @SerializedName("pressure")
        @Expose
        var pressure: Double? = null
        @SerializedName("windSpeed")
        @Expose
        var windSpeed: Double? = null
        @SerializedName("windGust")
        @Expose
        var windGust: Double? = null
        @SerializedName("windGustTime")
        @Expose
        var windGustTime: Int? = null
        @SerializedName("windBearing")
        @Expose
        var windBearing: Int? = null
        @SerializedName("cloudCover")
        @Expose
        var cloudCover: Double? = null
        @SerializedName("uvIndex")
        @Expose
        var uvIndex: Int? = null
        @SerializedName("uvIndexTime")
        @Expose
        var uvIndexTime: Int? = null
        @SerializedName("visibility")
        @Expose
        var visibility: Int? = null
        @SerializedName("ozone")
        @Expose
        var ozone: Double? = null
        @SerializedName("temperatureMin")
        @Expose
        var temperatureMin: Double? = null
        @SerializedName("temperatureMinTime")
        @Expose
        var temperatureMinTime: Int? = null
        @SerializedName("temperatureMax")
        @Expose
        var temperatureMax: Double? = null
        @SerializedName("temperatureMaxTime")
        @Expose
        var temperatureMaxTime: Int? = null
        @SerializedName("apparentTemperatureMin")
        @Expose
        var apparentTemperatureMin: Double? = null
        @SerializedName("apparentTemperatureMinTime")
        @Expose
        var apparentTemperatureMinTime: Int? = null
        @SerializedName("apparentTemperatureMax")
        @Expose
        var apparentTemperatureMax: Double? = null
        @SerializedName("apparentTemperatureMaxTime")
        @Expose
        var apparentTemperatureMaxTime: Int? = null

    }

    inner class Flags {

        @SerializedName("sources")
        @Expose
        var sources: List<String>? = null
        @SerializedName("nearest-station")
        @Expose
        var nearestStation: Double? = null
        @SerializedName("units")
        @Expose
        var units: String? = null

    }

    inner class Hourly {

        @SerializedName("summary")
        @Expose
        var summary: String? = null
        @SerializedName("icon")
        @Expose
        var icon: String? = null
        @SerializedName("data")
        @Expose
        var dataHourlies: List<DataHourly>? = null

    }

    inner class Minutely {

        @SerializedName("summary")
        @Expose
        var summary: String? = null
        @SerializedName("icon")
        @Expose
        var icon: String? = null
        @SerializedName("data")
        @Expose
        var dataMinutelies: List<DataMinutely>? = null

    }

}
