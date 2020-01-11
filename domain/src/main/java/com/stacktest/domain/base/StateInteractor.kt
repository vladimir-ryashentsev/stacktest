package com.stacktest.domain.base

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

abstract class StateInteractor(
    private val scope: CoroutineScope
) {

    private val state = MutableLiveData<State>()
    fun getState(): LiveData<State> = state

    private var job: Job? = null

    abstract suspend fun action()

    @MainThread
    internal open suspend fun doAction() {
        if (job == null || !job!!.isActive)
            job = scope.launch { loadUntilSuccessAndEmitStates() }
    }

    private suspend fun loadUntilSuccessAndEmitStates() {
        while (true) {
            try {
                state.postValue(State.InProgress)
                action()
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