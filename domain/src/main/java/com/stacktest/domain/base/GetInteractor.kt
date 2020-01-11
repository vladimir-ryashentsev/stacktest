package com.stacktest.domain.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope

abstract class GetInteractor<T>(
    scope: CoroutineScope
) : StateInteractor(
    scope
) {

    private val data = MutableLiveData<T>()
    fun getData(): LiveData<T> = data

    internal abstract suspend fun getAction(): T

    final override suspend fun action() {
        data.postValue(getAction())
    }
}