package com.stacktest.repository.regions.network

import com.stacktest.domain.regions.Region

data class RegionsResponse(
    val response: Response
)

data class Response(
    val count: Int,
    val items: List<Region>
)
