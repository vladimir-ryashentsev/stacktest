package com.stacktest.domain.regions

import com.stacktest.domain.base.PagedGetInteractor
import com.stacktest.domain.countries.Country
import kotlin.coroutines.CoroutineContext

class RegionsInteractor(
    context: CoroutineContext,
    private val regionsRepository: RegionsRepository,
    itemsPerPage: Int
) : PagedGetInteractor<Long, Region>(
    context,
    itemsPerPage
) {

    override fun doActionAsync(params: Long?) {
        if (params == null)
            throw IllegalStateException("country must be passed as parameter of doAction")

        super.doActionAsync(params)
    }

    override suspend fun loadPageAction(offset: Int, limit: Int, params: Long?) =
        regionsRepository.getRegions(params!!, offset, limit)
}