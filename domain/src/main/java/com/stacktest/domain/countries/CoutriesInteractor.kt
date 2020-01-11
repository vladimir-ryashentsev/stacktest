package com.stacktest.domain.countries

import com.stacktest.domain.base.PagedGetInteractor
import kotlinx.coroutines.CoroutineScope

class CoutriesInteractor(
    scope: CoroutineScope,
    private val countriesRepository: CountriesRepository,
    itemsPerPage: Int
) : PagedGetInteractor<Country>(
    scope,
    itemsPerPage
) {
    override suspend fun loadPageAction(offset: Int, limit: Int) =
        countriesRepository.getCountries(offset, limit)
}