package com.mbojec.halo.model

class CurrentForecast(
    var id: Long,
    var cityName: String,
    var temp: String,
    var weatherDesc: String,
    var dayName: String,
    var weatherImage: Int,
    var currentTime: String,
    var backgroundColor: Int)