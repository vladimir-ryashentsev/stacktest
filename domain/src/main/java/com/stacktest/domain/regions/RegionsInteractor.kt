package com.stacktest.domain.regions

import com.stacktest.domain.base.PagedGetInteractor
import com.stacktest.domain.countries.Country
import kotlinx.coroutines.CoroutineScope

class RegionsInteractor(
    scope: CoroutineScope,
    private val regionsRepository: RegionsRepository,
    itemsPerPage: Int
) : PagedGetInteractor<Region>(
    scope,
    itemsPerPage
) {
    var country: Country? = null

    override suspend fun doAction() {
        if (country == null)
            throw IllegalStateException("country must be set")

        super.doAction()
    }

    override suspend fun loadPageAction(offset: Int, limit: Int) =
        regionsRepository.getRegions(country!!, offset, limit)
}