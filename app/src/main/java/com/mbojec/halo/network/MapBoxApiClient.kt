package com.mbojec.halo.network

import com.mbojec.halo.SearchCityList
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MapBoxApiClient {

    @GET("{cityName}.json")
    fun getCitySearchList(
        @Path("cityName") cityName: String,
        @Query("language") targetLanguage: String,
        @Query("types") types: String,
        @Query("autocomplete") autocomplete: String,
        @Query("limit") limit: String
    ): Observable<SearchCityList>

    @GET("{longitude},{latitude}.json")
    fun getCurrentCityData(
        @Path("longitude") longitude: String,
        @Path("latitude") latitude: String,
        @Query("language") targetLanguage: String,
        @Query("types") types: String
    ): Observable<SearchCityList>


}