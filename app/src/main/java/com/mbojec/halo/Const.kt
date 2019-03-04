package com.mbojec.halo

class Const {
    companion object {
        const val REQUEST_LOCATION_PERMISSIONS = 101
        const val PROTOCOL = "https"
        const val BASE_MAPBOX_GEOCODING_API_URL = "api.mapbox.com/geocoding/v5/mapbox.places/"
        const val BASE_DARK_SKY_API_URL ="api.darksky.net/forecast/"
        const val CURRENT_LOCATION_ID = 1
        const val CURRENT_LOCATION = "CURRENT_LOCATION"
        const val CURRENT_LOCATION_UPDATE_TIME = "CURRENT_LOCATION_UPDATE_TIME"
        const val DATA_SYNC_UPDATE_TIME = "CURRENT_LOCATION_UPDATE_TIME"
        const val UPDATE_TIME = 3600000
    }
}