package com.stacktest.domain.countries

import com.stacktest.domain.base.PagedGetInteractor

class CoutriesInteractor(
    private val countriesRepository: CountriesRepository,
    itemsPerPage: Int
) : PagedGetInteractor<Unit, Country>(
    itemsPerPage
) {
    override suspend fun loadPageAction(offset: Int, limit: Int, params: Unit?) =
        countriesRepository.getCountries(offset, limit)
}