package com.stacktest.domain.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

abstract class GetInteractor<Params, Result> : StateInteractor<Params>() {

    private val data = MutableLiveData<Result>()
    fun getData(): LiveData<Result> = data

    internal abstract suspend fun getAction(params: Params?): Result

    final override suspend fun action(params: Params?) {
        data.postValue(getAction(params))
    }
}