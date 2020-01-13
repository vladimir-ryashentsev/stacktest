package com.stacktest.repository.components.regions

import com.stacktest.domain.countries.Country
import com.stacktest.domain.regions.RegionsRepository
import com.stacktest.repository.components.RepositoriesKoinComponent
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.koin.core.get

class RegionsTest : RepositoriesKoinComponent() {

    @Test
    fun asd() = runBlocking {
        //given
        val repository = get<RegionsRepository>()

        //when
        val regionsPage1 = repository.getRegions(COUNTRY, 0, 5)
        val regionsPage1Try2 = repository.getRegions(COUNTRY, 0, 5)
        val regionsPage2 = repository.getRegions(COUNTRY, 5, 5)

        //then
        //right size
        assertEquals(5, regionsPage1.size)
        //countries from local storage is the same as remote countries
        assertEquals(regionsPage1, regionsPage1Try2)
        //right size of page 2
        assertEquals(5, regionsPage2.size)
        //page 1 and page 2 are not intersected
        assertEquals(5, regionsPage1.minus(regionsPage2).size)
    }

    companion object {
        val COUNTRY = Country(30, "Afghanistan")
    }
}