package com.stacktest.repository.countries

import com.stacktest.domain.countries.Country

interface CountriesStorage : CountriesDataSource {
    suspend fun store(page: List<Country>)
}