package com.stacktest.repository.regions.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RegionDao {
    @Query("SELECT * FROM region WHERE countryId = (:countryId) ORDER BY title ASC LIMIT (:offset), (:limit)")
    suspend fun getRegions(countryId: Long, offset: Int, limit: Int): List<Region>

    @Insert
    suspend fun store(page: List<Region>)
}