package com.stacktest.repository.countries.network

import com.stacktest.domain.countries.Country

data class CountriesResponse(
    val response: Response
)

data class Response(
    val count: Int,
    val items: List<Country>
)
