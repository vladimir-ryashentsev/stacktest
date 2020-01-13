package com.stacktest.repository.regions.persistence

import com.nhaarman.mockitokotlin2.*
import com.stacktest.domain.regions.Region
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Test

@ExperimentalCoroutinesApi
class RegionsStorageImplTest {

    @Test
    fun `store call must transitively call dao#store`() = runBlockingTest {
        //given
        val dao = mock<RegionDao>()
        val storage = RegionsStorageImpl(dao)

        //when
        storage.store(REGIONS, COUNTRY_ID)

        //then
        verify(storage.dao).store(REGIONS.toPersistance(COUNTRY_ID))
    }

    @Test
    fun `getRegions call must transitively call dao#getRegions`() = runBlockingTest {
        //given
        val dao = mock<RegionDao>()
        whenever(dao.getRegions(eq(COUNTRY_ID), any(), any())).then {
            REGIONS.toPersistance(COUNTRY_ID)
        }
        val storage = RegionsStorageImpl(dao)

        //when
        val countries = storage.getRegions(COUNTRY_ID, 0, 2)

        //then
        verify(storage.dao).getRegions(COUNTRY_ID, 0, 2)
        assertEquals(REGIONS, countries)
    }

    internal fun List<Region>.toPersistance(countryId: Long) = this.map {
        Region(
            it.id,
            it.title,
            countryId
        )
    }

    companion object {
        val COUNTRY_ID = 0L
        val REGIONS = listOf(
            Region(0, "Томская область"),
            Region(1, "Красноярский край"),
            Region(2, "Республика Татарстан")
        )
    }
}