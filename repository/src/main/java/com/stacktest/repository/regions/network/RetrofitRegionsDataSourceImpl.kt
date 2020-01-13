package com.stacktest.repository.countries.network

import com.stacktest.repository.regions.RegionsDataSource

class RetrofitRegionsDataSourceImpl(
    internal val retrofitApi: RetrofitRegionsApi
) : RegionsDataSource {

    override suspend fun getRegions(countryId: Long, offset: Int, limit: Int) =
        retrofitApi.getRegions(offset, limit, countryId).response.items

}