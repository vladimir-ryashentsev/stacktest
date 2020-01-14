package com.stacktest.repository.regions

import com.stacktest.domain.regions.Region
import com.stacktest.domain.regions.RegionsRepository

class RegionsRepositoryImpl(
    internal val localStorage: RegionsStorage,
    internal val remoteDataSource: RegionsDataSource
) : RegionsRepository {
    override suspend fun getRegions(countryId: Long, offset: Int, limit: Int): List<Region> {
        var page = localStorage.getRegions(countryId, offset, limit)
        if (page.isNotEmpty())
            return page

        page = remoteDataSource.getRegions(countryId, offset, limit)
        localStorage.store(page, countryId)

        return page
    }

}