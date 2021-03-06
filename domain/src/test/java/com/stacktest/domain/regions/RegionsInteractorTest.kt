package com.stacktest.domain.regions

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.stacktest.domain.base.LiveDataTest
import com.stacktest.domain.countries.Country
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.coroutines.CoroutineContext

@ExperimentalCoroutinesApi
class RegionsInteractorTest : LiveDataTest() {

    @Test
    fun `must throw IllegalStateException is case of calling doAction without country`() =
        runBlockingTest {
            //given
            val interactor = successInteractor(coroutineContext)

            try {
                //when
                interactor.doActionAsync()

                //then
                assert(false) { "IllegalStateException must be thrown without country" }
            } catch (e: IllegalStateException) {

            }
        }

    @Test
    fun `data must contain all regions`() = runBlockingTest {
        //given
        val interactor = successInteractor(coroutineContext)

        //when
        repeat(10) {
            interactor.doActionAsync(COUNTRY)
        }

        //then
        assertEquals(REGIONS, interactor.getData().value)
    }

    suspend fun successInteractor(context: CoroutineContext): RegionsInteractor {
        val countriesRepository = mock<RegionsRepository>()
        whenever(countriesRepository.getRegions(any(), any(), any())).then { listOf<Region>() }
        whenever(countriesRepository.getRegions(any(), eq(0), eq(2))).then { REGIONS.subList(0, 2) }
        whenever(countriesRepository.getRegions(any(), eq(2), eq(2))).then { REGIONS.subList(2, 3) }

        val interactor = RegionsInteractor(
            context,
            countriesRepository,
            ITEMS_PER_PAGE
        )
        return interactor
    }

    companion object {
        const val ITEMS_PER_PAGE = 2
        val REGIONS = listOf(
            Region(0, "Алтайский край"),
            Region(0, "Новосибирская область"),
            Region(0, "Красноярский край")
        )
        val COUNTRY = Country(0, "РФ")
    }

}