package com.stacktest.repository.countries

import com.nhaarman.mockitokotlin2.*
import com.stacktest.domain.countries.Country
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

@ExperimentalCoroutinesApi
class CountriesRepositoryImplTest {

    @Test
    fun `data received from remote CountriesDataSource must be stored to local storage`() =
        runBlockingTest {
            //given
            val repository = repositoryWithoutLocalData()

            //when
            repository.getCountries(0, 123)

            //then
            verify(repository.localStorage).store(REMOTE_COUNTRIES)
        }

    @Test
    fun `remote CountriesDataSource is not used and data is not stored there in case of data exists in local storage`() =
        runBlockingTest {
            //given
            val repository = repositoryWithLocalData()

            //when
            repository.getCountries(0, 123)

            //then
            verify(repository.localStorage).getCountries(0, 123)
            verify(repository.remoteDataSource, times(0)).getCountries(0, 123)
            verify(repository.localStorage, times(0)).store(REMOTE_COUNTRIES)
        }

    suspend fun repositoryWithoutLocalData(): CountriesRepositoryImpl {
        val localStorage = mock<CountriesStorage>()
        whenever(localStorage.getCountries(any(), any())).then { listOf<List<Country>>() }

        val remoteDataSource = mock<CountriesDataSource>()
        whenever(remoteDataSource.getCountries(any(), any())).then { REMOTE_COUNTRIES }

        return CountriesRepositoryImpl(
            localStorage,
            remoteDataSource
        )
    }

    suspend fun repositoryWithLocalData(): CountriesRepositoryImpl {
        val localStorage = mock<CountriesStorage>()
        whenever(localStorage.getCountries(any(), any())).then { REMOTE_COUNTRIES }

        val remoteDataSource = mock<CountriesDataSource>()
        whenever(remoteDataSource.getCountries(any(), any())).then { REMOTE_COUNTRIES }

        return CountriesRepositoryImpl(
            localStorage,
            remoteDataSource
        )
    }

    companion object {
        val REMOTE_COUNTRIES = listOf(
            Country(0, "РФ"),
            Country(1, "Украина"),
            Country(2, "Азербайджан")
        )
    }
}