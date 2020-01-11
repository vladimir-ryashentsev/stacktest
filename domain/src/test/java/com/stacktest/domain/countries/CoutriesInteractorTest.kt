package com.stacktest.domain.countries

import com.stacktest.domain.base.LiveDataTest
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Test

@ExperimentalCoroutinesApi
class CoutriesInteractorTest : LiveDataTest() {

    @Test
    fun `data must contain all countries`() = runBlockingTest {
        //given
        val interactor = successInteractor(this)

        //when
        repeat(10) {
            interactor.doAction()
        }

        //then
        assertEquals(COUNTRIES, interactor.getData().value)
    }

    suspend fun successInteractor(scope: CoroutineScope): CoutriesInteractor {
        val countriesRepository = mock<CountriesRepository>()
        whenever(countriesRepository.getCountries(any(), any())).then { listOf<Country>() }
        whenever(countriesRepository.getCountries(0, 2)).then { COUNTRIES.subList(0, 2) }
        whenever(countriesRepository.getCountries(2, 2)).then { COUNTRIES.subList(2, 3) }
        return CoutriesInteractor(
            scope,
            countriesRepository,
            ITEMS_PER_PAGE
        )
    }

    companion object {
        const val ITEMS_PER_PAGE = 2
        val COUNTRIES = listOf(
            Country(0, "РФ"),
            Country(0, "Украина"),
            Country(0, "Азербайджан")
        )
    }

}