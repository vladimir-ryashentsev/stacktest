package com.stacktest.repository.countries.persistence

import com.stacktest.repository.countries.CountriesStorage
import com.stacktest.domain.countries.Country as DomainCountry

class CountriesStorageImpl(
    internal val dao: CountryDao
) : CountriesStorage {

    override suspend fun store(page: List<DomainCountry>) {
        dao.store(page.toPersistance())
    }

    override suspend fun getCountries(offset: Int, limit: Int): List<DomainCountry> {
        return dao.getCountries(offset, limit).toDomain()
    }

    internal fun List<Country>.toDomain() = this.map { DomainCountry(it.id, it.title) }
    internal fun List<DomainCountry>.toPersistance() = this.map {
        Country(
            it.id,
            it.title
        )
    }

}