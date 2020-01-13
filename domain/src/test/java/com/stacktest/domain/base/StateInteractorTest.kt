package com.stacktest.domain.base

import com.stacktest.domain.base.StateInteractor.Companion.RETRY_PERIOD_MS
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.concurrent.thread


@ExperimentalCoroutinesApi
class StateInteractorTest : LiveDataTest() {

    @Test
    fun `state must be InProgress right after doAction call`() = runBlockingTest {

        //given
        val interactor = successInteractor(this)

        //when
        interactor.doAction()
        delay(10)

        //then
        assertEquals(State.InProgress, interactor.getState().value)
    }

    @Test
    fun `state must be Success after doAction finishes`() = runBlockingTest {
        //given
        val interactor = successInteractor(this)

        //when
        interactor.doAction()
        delay(ACTION_DURATION + 1)

        //then
        assertEquals(State.Success, interactor.getState().value)
    }

    @Test
    fun `state must be Error is case of doAction throws exception`() = runBlockingTest {
        //given
        val interactor = failOnFirstTimeInteractor(this)

        //when
        interactor.doAction()
        delay(ACTION_DURATION + 1)

        //then
        assert(interactor.getState().value is State.Error)

        //wait for coroutine finishes
        delay(RETRY_PERIOD_MS)
    }

    @Test
    fun `state must be InProgress right after the next try after fail`() = runBlockingTest {
        //given
        val interactor = failOnFirstTimeInteractor(this)

        //when
        interactor.doAction()
        delay(ACTION_DURATION + RETRY_PERIOD_MS + 1)

        //then
        assertEquals(State.InProgress, interactor.getState().value)
    }

    @Test
    fun `state must be Succes when action finishes successfully on the second try`() =
        runBlockingTest {
            //given
            val interactor = failOnFirstTimeInteractor(this)

            //when
            interactor.doAction()
            delay(ACTION_DURATION + RETRY_PERIOD_MS + +ACTION_DURATION + 1)

            //then
            assertEquals(State.Success, interactor.getState().value)
        }

    private fun successInteractor(scope: CoroutineScope) =
        object : StateInteractor<Unit>(scope) {
            override suspend fun action(params: Unit?) {
                delay(ACTION_DURATION)
            }
        }

    private fun failOnFirstTimeInteractor(scope: CoroutineScope): StateInteractor<Unit> {
        return object : StateInteractor<Unit>(scope) {
            var firstTime = true
            override suspend fun action(params: Unit?) {
                delay(ACTION_DURATION)
                if (firstTime) {
                    firstTime = false
                    throw Exception()
                }
            }
        }
    }

    companion object {
        const val ACTION_DURATION = 100L
    }
}