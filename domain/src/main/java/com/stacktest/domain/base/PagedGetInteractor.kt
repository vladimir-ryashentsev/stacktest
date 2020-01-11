package com.stacktest.domain.base

import kotlinx.coroutines.CoroutineScope

abstract class PagedGetInteractor<T>(
    scope: CoroutineScope,
    private val itemsPerPage: Int
) : GetInteractor<List<T>>(
    scope
) {

    private var itemsLoaded = 0
    private var hasMore = true

    override suspend fun doAction() {
        if (hasMore)
            super.doAction()
    }

    final override suspend fun getAction(): List<T> {
        val page = loadPageAction(itemsLoaded, itemsPerPage)

        if (page.size < itemsPerPage)
            hasMore = false

        itemsLoaded += page.size
        if (getData().value != null) {
            val allItems = mutableListOf<T>()
            allItems.addAll(getData().value!!)
            allItems.addAll(page)
            return allItems
        }
        return page
    }

    abstract suspend fun loadPageAction(offset: Int, limit: Int): List<T>
}