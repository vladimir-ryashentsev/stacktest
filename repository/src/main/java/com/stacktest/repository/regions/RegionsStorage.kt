package com.stacktest.repository.regions

import com.stacktest.domain.regions.Region

interface RegionsStorage : RegionsDataSource {
    suspend fun store(page: List<Region>, countryId: Long)
}