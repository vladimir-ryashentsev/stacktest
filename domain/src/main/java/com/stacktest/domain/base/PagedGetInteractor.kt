package com.stacktest.domain.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger
import kotlin.coroutines.CoroutineContext

abstract class PagedGetInteractor<Params, Result>(
    context: CoroutineContext,
    private val itemsPerPage: Int
) : GetInteractor<Params, List<Result>>(context) {

    private val isCompletelyLoaded = MutableLiveData<Boolean>().also { it.postValue(false) }
    private var itemsLoaded = AtomicInteger(0)
    private var hasMore = AtomicBoolean(true)

    fun isCompletelyLoaded(): LiveData<Boolean> = isCompletelyLoaded

    override fun doActionAsync(params: Params?) {
        if (hasMore.get())
            super.doActionAsync(params)
    }

    final override suspend fun getAction(params: Params?): List<Result> {
        val page = loadPageAction(itemsLoaded.get(), itemsPerPage, params)

        if (page.size < itemsPerPage) {
            isCompletelyLoaded.postValue(true)
            hasMore.set(false)
        }

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