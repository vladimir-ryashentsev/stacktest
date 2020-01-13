package com.stacktest.repository.countries

import com.stacktest.domain.countries.CountriesRepository
import com.stacktest.domain.countries.Country

class CountriesRepositoryImpl(
    internal val localStorage: CountriesStorage,
    internal val remoteDataSource: CountriesDataSource
) : CountriesRepository {
    override suspend fun getCountries(offset: Int, limit: Int): List<Country> {
        var page = localStorage.getCountries(offset, limit)
        if (page.isNotEmpty())
            return page

        page = remoteDataSource.getCountries(offset, limit)
        localStorage.store(page)

        return page
    }

}