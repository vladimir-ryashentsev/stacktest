package com.stacktest.repository.regions

import com.nhaarman.mockitokotlin2.*
import com.stacktest.domain.countries.Country
import com.stacktest.domain.regions.Region
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

@ExperimentalCoroutinesApi
class RegionsRepositoryImplTest {

    @Test
    fun `data received from remote RegionsDataSource must be stored to local storage`() =
        runBlockingTest {
            //given
            val repository = repositoryWithoutLocalData(COUNTRY.id)

            //when
            repository.getRegions(COUNTRY, 0, 123)

            //then
            verify(repository.localStorage).store(REMOTE_REGIONS, COUNTRY.id)
        }

    @Test
    fun `remote RegionsDataSource is not used and data is not stored there in case of data exists in local storage`() =
        runBlockingTest {
            //given
            val repository = repositoryWithLocalData(COUNTRY.id)

            //when
            repository.getRegions(COUNTRY, 0, 123)

            //then
            verify(repository.localStorage).getRegions(COUNTRY.id, 0, 123)
            verify(repository.remoteDataSource, times(0)).getRegions(COUNTRY.id, 0, 123)
            verify(repository.localStorage, times(0)).store(REMOTE_REGIONS, COUNTRY.id)
        }

    suspend fun repositoryWithoutLocalData(countryId: Long): RegionsRepositoryImpl {
        val localStorage = mock<RegionsStorage>()
        whenever(
            localStorage.getRegions(
                eq(countryId),
                any(),
                any()
            )
        ).then { listOf<List<Country>>() }

        val remoteDataSource = mock<RegionsDataSource>()
        whenever(remoteDataSource.getRegions(eq(countryId), any(), any())).then { REMOTE_REGIONS }

        return RegionsRepositoryImpl(
            localStorage,
            remoteDataSource
        )
    }

    suspend fun repositoryWithLocalData(countryId: Long): RegionsRepositoryImpl {
        val localStorage = mock<RegionsStorage>()
        whenever(localStorage.getRegions(eq(countryId), any(), any())).then { REMOTE_REGIONS }

        val remoteDataSource = mock<RegionsDataSource>()
        whenever(remoteDataSource.getRegions(eq(countryId), any(), any())).then { REMOTE_REGIONS }

        return RegionsRepositoryImpl(
            localStorage,
            remoteDataSource
        )
    }

    companion object {
        val COUNTRY = Country(0, "РФ")
        val REMOTE_REGIONS = listOf(
            Region(0, "Томская область"),
            Region(1, "Красноярский край"),
            Region(2, "Республика Татарстан")
        )
    }
}