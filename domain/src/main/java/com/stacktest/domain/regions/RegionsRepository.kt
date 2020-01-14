package com.stacktest.domain.regions

interface RegionsRepository {
    suspend fun getRegions(countryId: Long, offset: Int, limit: Int): List<Region>
}