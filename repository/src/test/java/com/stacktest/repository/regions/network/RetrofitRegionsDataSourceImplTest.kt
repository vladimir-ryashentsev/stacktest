package com.stacktest.repository.regions.network

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.stacktest.domain.regions.Region
import com.stacktest.repository.countries.network.RetrofitRegionsApi
import com.stacktest.repository.countries.network.RetrofitRegionsDataSourceImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Test

@ExperimentalCoroutinesApi
class RetrofitRegionsDataSourceImplTest {

    @Test
    fun `getRegions call must transitively call retrofitRegionsApi#getRegions`() =
        runBlockingTest {
            //given
            val retrofitApi = mock<RetrofitRegionsApi>()
            whenever(retrofitApi.getRegions(0, 2, COUNTRY_ID)).then { REGIONS_RESPONSE }
            val remoteDataSource = RetrofitRegionsDataSourceImpl(retrofitApi)

            //when
            val regions = remoteDataSource.getRegions(COUNTRY_ID, 0, 2)

            //then
            verify(retrofitApi).getRegions(0, 2, COUNTRY_ID)
            assertEquals(REGIONS, regions)
        }

    companion object {
        val COUNTRY_ID = 0L
        val REGIONS = listOf(
            Region(0, "Томская область"),
            Region(1, "Красноярский край"),
            Region(2, "Республика Татарстан")
        )
        val REGIONS_RESPONSE = RegionsResponse(Response(3, REGIONS))
    }
}