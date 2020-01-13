package com.stacktest.repository.countries.network

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.stacktest.domain.countries.Country
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Test

@ExperimentalCoroutinesApi
class RetrofitCountriesDataSourceImplTest {

    @Test
    fun `getCountries call must transitively call retrofitCountriesApi#getCountries`() =
        runBlockingTest {
            //given
            val retrofitApi = mock<RetrofitCountriesApi>()
            whenever(retrofitApi.getCountries(0, 2)).then { COUNTRIES_RESPONSE }
            val remoteDataSource = RetrofitCountriesDataSourceImpl(retrofitApi)

            //when
            val countries = remoteDataSource.getCountries(0, 2)

            //then
            verify(retrofitApi).getCountries(0, 2)
            assertEquals(COUNTRIES, countries)
        }

    companion object {
        val COUNTRIES = listOf(
            Country(0, "РФ"),
            Country(1, "Украина"),
            Country(2, "Казахстан")
        )
        val COUNTRIES_RESPONSE = CountriesResponse(Response(3, COUNTRIES))
    }
}