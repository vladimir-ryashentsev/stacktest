package com.stacktest.repository.countries.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CountryDao {
    @Query("SELECT * FROM country ORDER BY title ASC LIMIT (:offset), (:limit)")
    suspend fun getCountries(offset: Int, limit: Int): List<Country>

    @Insert
    suspend fun store(page: List<Country>)
}