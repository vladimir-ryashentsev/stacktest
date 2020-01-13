package com.stacktest.repository.regions.persistence

import com.stacktest.repository.regions.RegionsStorage
import com.stacktest.domain.regions.Region as DomainRegion

class RegionsStorageImpl(
    internal val dao: RegionDao
) : RegionsStorage {

    override suspend fun store(page: List<DomainRegion>, countryId: Long) {
        dao.store(page.toPersistance(countryId))
    }

    override suspend fun getRegions(
        countryId: Long,
        offset: Int,
        limit: Int
    ): List<DomainRegion> {
        return dao.getRegions(countryId, offset, limit).toDomain()
    }

    internal fun List<Region>.toDomain() = this.map { DomainRegion(it.id, it.title) }
    internal fun List<DomainRegion>.toPersistance(countryId: Long) = this.map {
        Region(
            it.id,
            it.title,
            countryId
        )
    }

}