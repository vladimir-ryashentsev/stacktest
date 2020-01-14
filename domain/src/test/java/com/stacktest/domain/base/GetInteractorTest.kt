package com.stacktest.domain.base

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import kotlin.coroutines.CoroutineContext

@ExperimentalCoroutinesApi
class GetInteractorTest : LiveDataTest() {

    @Test
    fun `data must be null and state must be InProgress right after doAction started`() =
        runBlockingTest {
            //given
            val interactor = successInteractor(coroutineContext)

            //when
            interactor.doActionAsync()
            delay(10)

            //then
            assertNull(interactor.getData().value)
            assertEquals(State.InProgress, interactor.getState().value)
        }

    @Test
    fun `must generate data and set state to Success on getAction finishes`() = runBlockingTest {
        //given
        val interactor = successInteractor(coroutineContext)

        //when
        interactor.doActionAsync()
        delay(ACTION_DURATION + 10)

        //then
        assertEquals(DATA, interactor.getData().value)
        assertEquals(State.Success, interactor.getState().value)
    }

    @Test
    fun `data must be null and state must be Error right after fail`() = runBlockingTest {
        //given
        val interactor = failOnFirstTimeInteractor(coroutineContext)

        //when
        interactor.doActionAsync()
        delay(ACTION_DURATION + 1)

        //then
        assertNull(interactor.getData().value)
        assert(interactor.getState().value is State.Error)
    }

    fun successInteractor(context: CoroutineContext): GetInteractor<Unit, Int> {
        val interactor = object : GetInteractor<Unit, Int>(context) {
            override suspend fun getAction(params: Unit?): Int {
                delay(ACTION_DURATION)
                return DATA
            }
        }
        return interactor
    }

    private fun failOnFirstTimeInteractor(context: CoroutineContext): GetInteractor<Unit, Int> {
        return object : GetInteractor<Unit, Int>(context) {
            var firstTime = true
            override suspend fun getAction(params: Unit?): Int {
                delay(ACTION_DURATION)
                if (firstTime) {
                    firstTime = false
                    throw Exception()
                }
                return DATA
            }
        }
    }

    companion object {
        const val ACTION_DURATION = 100L
        const val DATA = 42
    }
}