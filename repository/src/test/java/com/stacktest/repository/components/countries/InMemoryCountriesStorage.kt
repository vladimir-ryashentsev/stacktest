package com.stacktest.repository.components.countries

import com.stacktest.domain.countries.Country
import com.stacktest.repository.countries.CountriesStorage

class InMemoryCountriesStorage : CountriesStorage {

    private val countries = mutableListOf<Country>()

    @Synchronized
    override suspend fun store(page: List<Country>) {
        countries.addAll(page)
    }

    @Synchronized
    override suspend fun getCountries(offset: Int, limit: Int): List<Country> {
        if (offset >= countries.size)
            return emptyList()

        val l = if (offset + limit > countries.size)
            countries.size - offset
        else
            limit

        return countries.subList(offset, offset + l).toList()
    }

}