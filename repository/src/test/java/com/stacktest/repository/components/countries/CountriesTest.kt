package com.stacktest.repository.components.countries

import com.stacktest.domain.countries.CountriesRepository
import com.stacktest.repository.components.RepositoriesKoinComponent
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.koin.core.get

class CountriesTest : RepositoriesKoinComponent() {

    @Test
    fun asd() = runBlocking {
        //given
        val repository = get<CountriesRepository>()

        //when
        val countriesPage1 = repository.getCountries(0, 5)
        val countriesPage1Try2 = repository.getCountries(0, 5)
        val countriesPage2 = repository.getCountries(5, 5)

        //then
        //right size
        assertEquals(5, countriesPage1.size)
        //countries from local storage is the same as remote countries
        assertEquals(countriesPage1, countriesPage1Try2)
        //right size of page 2
        assertEquals(5, countriesPage2.size)
        //page 1 and page 2 are not intersected
        assertEquals(5, countriesPage1.minus(countriesPage2).size)
    }
}