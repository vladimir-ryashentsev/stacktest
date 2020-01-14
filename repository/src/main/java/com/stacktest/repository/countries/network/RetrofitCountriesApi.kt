package com.stacktest.repository.countries.network

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface RetrofitCountriesApi {
    @Headers("Content-Type: application/json")
    @GET("database.getCountries")
    suspend fun getCountries(
        @Query("offset") offset: Int,
        @Query("count") limit: Int,
        @Query("access_token") accessToken: String = "58abf24258abf24258abf2420958ecdd3c558ab58abf2420323181c5f469332b0e2644d",
        @Query("v") version: String = "5.103",
        @Query("need_all") needAll: Int = 1
    ): CountriesResponse
}