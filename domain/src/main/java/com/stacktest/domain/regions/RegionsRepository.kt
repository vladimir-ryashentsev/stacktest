package com.stacktest.domain.regions

import com.stacktest.domain.countries.Country

interface RegionsRepository {
    suspend fun getRegions(country: Country, offset: Int, limit: Int): List<Region>
}