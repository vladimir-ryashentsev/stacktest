package com.stacktest.domain.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlin.coroutines.CoroutineContext

abstract class GetInteractor<Params, Result>(context: CoroutineContext) :
    StateInteractor<Params>(context) {

    private val data = MutableLiveData<Result>()
    fun getData(): LiveData<Result> = data

    internal abstract suspend fun getAction(params: Params?): Result

    final override suspend fun action(params: Params?) {
        data.postValue(getAction(params))
    }
}