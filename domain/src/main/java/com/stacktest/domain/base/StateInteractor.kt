package com.stacktest.domain.base

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

abstract class StateInteractor<Params>(
    private val scope: CoroutineScope
) {

    private val state = MutableLiveData<State>()
    fun getState(): LiveData<State> = state

    private var job: Job? = null

    abstract suspend fun action(params: Params?)

    @MainThread
    internal open suspend fun doAction(params: Params? = null) {
        if (job == null || !job!!.isActive) {
            println("start job")
            job = scope.launch { loadUntilSuccessAndEmitStates(params) }
        }
    }

    private suspend fun loadUntilSuccessAndEmitStates(params: Params?) {
        println("loadUntilSuccessAndEmitStates")
        while (true) {
            try {
                state.postValue(State.InProgress)
                action(params)
                state.postValue(State.Success)
                break
            } catch (e: Exception) {
                state.postValue(State.Error("Can't load.\nPlease, try again later."))
                delay(RETRY_PERIOD_MS)
            }
        }
    }

    companion object {
        internal const val RETRY_PERIOD_MS = 5_000L
    }
}