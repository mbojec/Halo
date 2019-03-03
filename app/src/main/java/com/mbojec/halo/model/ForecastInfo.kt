package com.mbojec.halo.model

class ForecastInfo(var id: Long,
                   var pressure: String,
                   var humidity: String,
                   var windSpeed: String,
                   var windDirection: String,
                   var sunset: String,
                   var sunrise: String,
                   var precip: String,
                   var cloudiness: String,
                   var uvIndex: String,
                   var backgroundColor: Int)