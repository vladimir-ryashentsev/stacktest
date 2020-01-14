package com.stacktest.domain.base

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

abstract class StateInteractor<Params>(private val context: CoroutineContext) {

    private val state = MutableLiveData<State>()
    fun getState(): LiveData<State> = state

    private var isInProgress = false

    abstract suspend fun action(params: Params?)

    @MainThread
    open fun doActionAsync(params: Params? = null) {
        if (!isInProgress) {
            isInProgress = true
            CoroutineScope(context).launch {
                loadUntilSuccessAndEmitStates(params)
            }
            isInProgress = false
        }
    }

    private suspend fun loadUntilSuccessAndEmitStates(params: Params?) {
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