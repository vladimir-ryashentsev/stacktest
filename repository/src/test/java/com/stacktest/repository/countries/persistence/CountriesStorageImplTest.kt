package com.stacktest.repository.countries.persistence

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.stacktest.domain.countries.Country
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Test

@ExperimentalCoroutinesApi
class CountriesStorageImplTest {

    @Test
    fun `store call must transitively call dao#store`() = runBlockingTest {
        //given
        val dao = mock<CountryDao>()
        val storage = CountriesStorageImpl(dao)

        //when
        storage.store(COUNTRIES)

        //then
        verify(storage.dao).store(COUNTRIES.toPersistance())
    }

    @Test
    fun `getCountries call must transitively call dao#getCountries`() = runBlockingTest {
        //given
        val dao = mock<CountryDao>()
        whenever(dao.getCountries(any(), any())).then { COUNTRIES.toPersistance() }
        val storage = CountriesStorageImpl(dao)

        //when
        val countries = storage.getCountries(0, 2)

        //then
        verify(storage.dao).getCountries(0, 2)
        assertEquals(COUNTRIES, countries)
    }

    internal fun List<Country>.toPersistance() = this.map {
        com.stacktest.repository.countries.persistence.Country(
            it.id,
            it.title
        )
    }

    companion object {
        val COUNTRIES = listOf(
            Country(0, "РФ"),
            Country(1, "Украина"),
            Country(2, "Казахстан")
        )
    }
}