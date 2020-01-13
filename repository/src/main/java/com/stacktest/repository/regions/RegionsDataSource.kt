package com.stacktest.repository.regions

import com.stacktest.domain.regions.Region

interface RegionsDataSource {
    suspend fun getRegions(countryId: Long, offset: Int, limit: Int): List<Region>
}