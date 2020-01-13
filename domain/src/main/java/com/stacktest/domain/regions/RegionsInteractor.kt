package com.stacktest.domain.regions

import com.stacktest.domain.base.PagedGetInteractor
import com.stacktest.domain.countries.Country

class RegionsInteractor(
    private val regionsRepository: RegionsRepository,
    itemsPerPage: Int
) : PagedGetInteractor<Country, Region>(
    itemsPerPage
) {

    override suspend fun doAction(params: Country?) {
        if (params == null)
            throw IllegalStateException("country must be passed as parameter of doAction")

        super.doAction(params)
    }

    override suspend fun loadPageAction(offset: Int, limit: Int, params: Country?) =
        regionsRepository.getRegions(params!!, offset, limit)
}