package com.mbojec.halo

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
}