package com.stacktest.repository.countries.network

import com.stacktest.repository.regions.network.RegionsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitRegionsApi {
    @GET("database.getRegions")
    suspend fun getRegions(
        @Query("offset") offset: Int,
        @Query("count") limit: Int,
        @Query("country_id") countryId: Long,
        @Query("access_token") accessToken: String = "58abf24258abf24258abf2420958ecdd3c558ab58abf2420323181c5f469332b0e2644d",
        @Query("v") version: String = "5.103"
    ): RegionsResponse
}