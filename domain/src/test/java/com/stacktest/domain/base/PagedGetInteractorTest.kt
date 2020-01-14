package com.stacktest.domain.base

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import kotlin.coroutines.CoroutineContext

@ExperimentalCoroutinesApi
class PagedGetInteractorTest : LiveDataTest() {

    @Test
    fun `data must contain 1 page and state must be Error right after fail to load second page`() =
        runBlockingTest {
            //given
            val interactor = secondPageFailInteractor(coroutineContext)

            //when
            repeat(2) {
                interactor.doActionAsync()
                delay(ACTION_DURATION + 1)
            }

            //then
            assertEquals(
                DATA.subList(
                    0,
                    ITEMS_PER_PAGE
                ), interactor.getData().value
            )
            assert(interactor.getState().value is State.Error)
        }

    @Test
    fun `data must be null and state must be InProgress right after doAction call`() =
        runBlockingTest {
            //given
            val interactor = fourPagesInteractor(coroutineContext)

            //when
            interactor.doActionAsync()
            delay(10)

            //then
            assertNull(interactor.getData().value)
            assertEquals(State.InProgress, interactor.getState().value)
        }

    @Test
    fun `data must contain first page and state must be Success on action finished`() =
        runBlockingTest {
            //given
            val interactor = fourPagesInteractor(coroutineContext)

            //when
            interactor.doActionAsync()
            delay(ACTION_DURATION + 1)

            //then
            assertEquals(
                DATA.subList(
                    0,
                    ITEMS_PER_PAGE
                ), interactor.getData().value
            )
            assertEquals(State.Success, interactor.getState().value)
        }

    @Test
    fun `data must contain 3 pages and state must be Success on action finished 3 times`() =
        runBlockingTest {
            //given
            val interactor = fourPagesInteractor(coroutineContext)

            //when
            repeat(3) {
                interactor.doActionAsync()
                delay(ACTION_DURATION + 1)
            }

            //then
            assertEquals(DATA.subList(0, ITEMS_PER_PAGE * 3), interactor.getData().value)
            assertEquals(State.Success, interactor.getState().value)
        }

    @Test
    fun `data must contain 4 pages and state must be Success on action finished more then 4 times`() =
        runBlockingTest {
            //given
            val interactor = fourPagesInteractor(coroutineContext)

            //when
            repeat(10) {
                interactor.doActionAsync()
                delay(ACTION_DURATION + 1)
            }

            //then
            assertEquals(DATA, interactor.getData().value)
            assertEquals(State.Success, interactor.getState().value)
        }

    fun fourPagesInteractor(context: CoroutineContext): PagedGetInteractor<Unit, Int> {
        val interactor = object : PagedGetInteractor<Unit, Int>(context, ITEMS_PER_PAGE) {
            override suspend fun loadPageAction(offset: Int, limit: Int, params: Unit?): List<Int> {
                if (offset > DATA.size)
                    return listOf()

                var l = limit
                if (limit + offset > DATA.size)
                    l = DATA.size - offset


                delay(ACTION_DURATION)
                return DATA.subList(offset, offset + l)
            }
        }
        return interactor
    }

    fun secondPageFailInteractor(context: CoroutineContext): PagedGetInteractor<Unit, Int> {
        val interactor = object : PagedGetInteractor<Unit, Int>(context, ITEMS_PER_PAGE) {
            var firstTimeOfSecondPageLoading = true
            override suspend fun loadPageAction(offset: Int, limit: Int, params: Unit?): List<Int> {
                delay(ACTION_DURATION)

                if (offset > 0 && firstTimeOfSecondPageLoading) {
                    firstTimeOfSecondPageLoading = false
                    throw Exception()
                }

                if (offset > DATA.size)
                    return listOf()

                var l = limit
                if (limit + offset > DATA.size)
                    l = DATA.size - offset


                return DATA.subList(offset, offset + l)
            }
        }
        return interactor
    }

    companion object {
        const val ACTION_DURATION = 100L
        const val ITEMS_PER_PAGE = 3
        val DATA = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    }

}