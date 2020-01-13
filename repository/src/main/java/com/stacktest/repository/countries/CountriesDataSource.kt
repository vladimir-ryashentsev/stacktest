package com.stacktest.repository.countries

import com.stacktest.domain.countries.Country

interface CountriesDataSource {
    suspend fun getCountries(offset: Int, limit: Int): List<Country>
}