package com.stacktest.repository.countries.network

import com.stacktest.repository.countries.CountriesDataSource

class RetrofitCountriesDataSourceImpl(
    internal val retrofitApi: RetrofitCountriesApi
) : CountriesDataSource {

    override suspend fun getCountries(offset: Int, limit: Int) =
        retrofitApi.getCountries(offset, limit).response.items

}