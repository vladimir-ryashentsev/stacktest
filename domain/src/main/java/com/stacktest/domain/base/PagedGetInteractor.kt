package com.stacktest.domain.base

import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger

abstract class PagedGetInteractor<Params, Result>(
    private val itemsPerPage: Int
) : GetInteractor<Params, List<Result>>() {

    private var itemsLoaded = AtomicInteger(0)
    private var hasMore = AtomicBoolean(true)

    override suspend fun doAction(params: Params?) {
        if (hasMore.get())
            super.doAction(params)
    }

    final override suspend fun getAction(params: Params?): List<Result> {
        val page = loadPageAction(itemsLoaded.get(), itemsPerPage, params)

        if (page.size < itemsPerPage)
            hasMore.set(false)

        itemsLoaded.addAndGet(page.size)
        if (getData().value != null) {
            val allItems = mutableListOf<Result>()
            allItems.addAll(getData().value!!)
            allItems.addAll(page)
            return allItems
        }
        return page
    }

    abstract suspend fun loadPageAction(offset: Int, limit: Int, params: Params?): List<Result>
}