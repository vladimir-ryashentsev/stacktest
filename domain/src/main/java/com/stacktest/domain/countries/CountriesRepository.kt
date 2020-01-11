package com.stacktest.domain.countries

interface CountriesRepository {
    suspend fun getCountries(offset: Int, limit: Int): List<Country>
}