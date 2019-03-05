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

        const val KEY_CURRENT_LOCATION_UPDATE_TIME_LIMIT = "current_location_update_time_limit"
        const val KEY_CURRENT_LOCATION_UPDATE_DISTANCE_LIMIT = "current_location_update_distance_limit"
        const val KEY_DATA_UPDATE_TIME_LIMIT = "data_update_time_limit"
        const val KEY_UPDATE_REQUIRED = "force_update_required"
        const val KEY_CURRENT_VERSION = "force_update_current_version"
        const val KEY_UPDATE_URL = "force_update_store_url"
    }
}