package com.stacktest.domain.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope

abstract class GetInteractor<Params, Result>(
    scope: CoroutineScope
) : StateInteractor<Params>(
    scope
) {

    private val data = MutableLiveData<Result>()
    fun getData(): LiveData<Result> = data

    internal abstract suspend fun getAction(params: Params?): Result

    final override suspend fun action(params: Params?) {
        data.postValue(getAction(params))
    }
}