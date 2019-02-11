package com.mbojec.halo

import com.mbojec.halo.model.Forecast
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DarkSkyApiClient {

    @GET("{api_key}/{location}")
    fun getCityForecast(
        @Path("api_key") apiKey: String,
        @Path("location") location: String,
        @Query("lang") targetLanguage: String,
        @Query("units") unitType: String
    ): Observable<Forecast>
}