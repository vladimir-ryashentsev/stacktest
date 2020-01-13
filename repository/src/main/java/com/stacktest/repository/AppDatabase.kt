package com.stacktest.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import com.stacktest.repository.countries.persistence.Country
import com.stacktest.repository.countries.persistence.CountryDao
import com.stacktest.repository.regions.persistence.Region
import com.stacktest.repository.regions.persistence.RegionDao

@Database(entities = arrayOf(Country::class, Region::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun regionDao(): RegionDao
    abstract fun countryDao(): CountryDao
}