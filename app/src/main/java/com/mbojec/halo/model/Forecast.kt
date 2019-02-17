package com.mbojec.halo.model

import androidx.room.Embedded
import androidx.room.TypeConverters
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.mbojec.halo.database.converters.DataDailyConverter
import com.mbojec.halo.database.converters.DataHourlyConverter
import com.mbojec.halo.database.converters.DataMinutelyConverter
import com.mbojec.halo.database.converters.StringConverter

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
    @Embedded(prefix = "currently")
    var currently: Currently? = null

    @SerializedName("minutely")
    @Expose
    @Embedded(prefix = "minutely")
    var minutely: Minutely? = null

    @SerializedName("hourly")
    @Expose
    @Embedded(prefix = "hourly")
    var hourly: Hourly? = null

    @SerializedName("daily")
    @Expose
    @Embedded(prefix = "daily")
    var daily: Daily? = null

    @SerializedName("flags")
    @Expose
    @Embedded
    var flags: Flags? = null

    @SerializedName("offset")
    @Expose
    var offset: Int? = null

    class Currently {

        @SerializedName("time")
        @Expose
        var time: Long? = null

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
        var cloudCover: Double? = null

        @SerializedName("uvIndex")
        @Expose
        var uvIndex: Int? = null

        @SerializedName("visibility")
        @Expose
        var visibility: Double? = null

        @SerializedName("ozone")
        @Expose
        var ozone: Double? = null

    }


    class Daily {

        @SerializedName("summary")
        @Expose
        var summary: String? = null

        @SerializedName("icon")
        @Expose
        var icon: String? = null

        @SerializedName("data")
        @Expose
        @TypeConverters(DataDailyConverter::class)
        var dataDailies: List<DataDaily>? = null

    }

    class DataMinutely {

        @SerializedName("time")
        @Expose
        var time: Long? = null

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


    class DataHourly {

        @SerializedName("time")
        @Expose
        var time: Long? = null

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
        var cloudCover: Double? = null

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


    class DataDaily {

        @SerializedName("time")
        @Expose
        var time: Long? = null

        @SerializedName("summary")
        @Expose
        var summary: String? = null

        @SerializedName("icon")
        @Expose
        var icon: String? = null

        @SerializedName("sunriseTime")
        @Expose
        var sunriseTime: Long? = null

        @SerializedName("sunsetTime")
        @Expose
        var sunsetTime: Long? = null

        @SerializedName("moonPhase")
        @Expose
        var moonPhase: Double? = null

        @SerializedName("precipIntensity")
        @Expose
        var precipIntensity: Double? = null

        @SerializedName("precipIntensityMax")
        @Expose
        var precipIntensityMax: Double? = null

        @SerializedName("precipIntensityMaxTime")
        @Expose
        var precipIntensityMaxTime: Long? = null

        @SerializedName("precipProbability")
        @Expose
        var precipProbability: Double? = null
        @SerializedName("precipType")
        @Expose
        var precipType: String? = null

        @SerializedName("temperatureHigh")
        @Expose
        var temperatureHigh: Double? = null

        @SerializedName("temperatureHighTime")
        @Expose
        var temperatureHighTime: Long? = null

        @SerializedName("temperatureLow")
        @Expose
        var temperatureLow: Double? = null

        @SerializedName("temperatureLowTime")
        @Expose
        var temperatureLowTime: Long? = null

        @SerializedName("apparentTemperatureHigh")
        @Expose
        var apparentTemperatureHigh: Double? = null

        @SerializedName("apparentTemperatureHighTime")
        @Expose
        var apparentTemperatureHighTime: Long? = null

        @SerializedName("apparentTemperatureLow")
        @Expose
        var apparentTemperatureLow: Double? = null

        @SerializedName("apparentTemperatureLowTime")
        @Expose
        var apparentTemperatureLowTime: Long? = null

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
        var windGustTime: Long? = null

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
        var visibility: Double? = null

        @SerializedName("ozone")
        @Expose
        var ozone: Double? = null

        @SerializedName("temperatureMin")
        @Expose
        var temperatureMin: Double? = null

        @SerializedName("temperatureMinTime")
        @Expose
        var temperatureMinTime: Long? = null

        @SerializedName("temperatureMax")
        @Expose
        var temperatureMax: Double? = null

        @SerializedName("temperatureMaxTime")
        @Expose
        var temperatureMaxTime: Long? = null

        @SerializedName("apparentTemperatureMin")
        @Expose
        var apparentTemperatureMin: Double? = null

        @SerializedName("apparentTemperatureMinTime")
        @Expose
        var apparentTemperatureMinTime: Long? = null

        @SerializedName("apparentTemperatureMax")
        @Expose
        var apparentTemperatureMax: Double? = null

        @SerializedName("apparentTemperatureMaxTime")
        @Expose
        var apparentTemperatureMaxTime: Long? = null

    }

    class Flags {

        @SerializedName("sources")
        @Expose
        @TypeConverters(StringConverter::class)
        var sources: List<String>? = null

        @SerializedName("nearest-station")
        @Expose
        var nearestStation: Double? = null

        @SerializedName("units")
        @Expose
        var units: String? = null

    }

    class Hourly {

        @SerializedName("summary")
        @Expose
        var summary: String? = null

        @SerializedName("icon")
        @Expose
        var icon: String? = null

        @SerializedName("data")
        @Expose
        @TypeConverters(DataHourlyConverter::class)
        var dataHourlies: List<DataHourly>? = null

    }

    class Minutely {

        @SerializedName("summary")
        @Expose
        var summary: String? = null

        @SerializedName("icon")
        @Expose
        var icon: String? = null

        @SerializedName("data")
        @Expose
        @TypeConverters(DataMinutelyConverter::class)
        var dataMinutelies: List<DataMinutely>? = null

    }

}
