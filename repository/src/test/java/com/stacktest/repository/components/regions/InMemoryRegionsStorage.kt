package com.stacktest.repository.components.regions

import com.stacktest.domain.regions.Region
import com.stacktest.repository.regions.RegionsStorage

class InMemoryRegionsStorage : RegionsStorage {

    private val regions = mutableMapOf<Long, MutableList<Region>>()

    @Synchronized
    override suspend fun store(page: List<Region>, countryId: Long) {
        var list = regions[countryId]
        if (list == null) {
            list = mutableListOf()
            regions[countryId] = list
        }
        list.addAll(page)
    }

    @Synchronized
    override suspend fun getRegions(countryId: Long, offset: Int, limit: Int): List<Region> {
        val list = regions[countryId] ?: return emptyList()

        if (offset >= list.size)
            return emptyList()

        val l = if (offset + limit > list.size)
            list.size - offset
        else
            limit

        return list.subList(offset, offset + l).toList()
    }
}