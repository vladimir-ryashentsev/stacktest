package com.stacktest.domain.countries

import com.stacktest.domain.base.PagedGetInteractor
import kotlin.coroutines.CoroutineContext

class CountriesInteractor(
    context: CoroutineContext,
    private val countriesRepository: CountriesRepository,
    itemsPerPage: Int
) : PagedGetInteractor<Unit, Country>(
    context,
    itemsPerPage
) {
    override suspend fun loadPageAction(offset: Int, limit: Int, params: Unit?) =
        countriesRepository.getCountries(offset, limit)
}