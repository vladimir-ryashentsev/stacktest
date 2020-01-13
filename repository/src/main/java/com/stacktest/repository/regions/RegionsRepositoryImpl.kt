package com.stacktest.repository.regions

import com.stacktest.domain.countries.Country
import com.stacktest.domain.regions.Region
import com.stacktest.domain.regions.RegionsRepository

class RegionsRepositoryImpl(
    internal val localStorage: RegionsStorage,
    internal val remoteDataSource: RegionsDataSource
) : RegionsRepository {
    override suspend fun getRegions(country: Country, offset: Int, limit: Int): List<Region> {
        var page = localStorage.getRegions(country.id, offset, limit)
        if (page.isNotEmpty())
            return page

        page = remoteDataSource.getRegions(country.id, offset, limit)
        localStorage.store(page, country.id)

        return page
    }

}