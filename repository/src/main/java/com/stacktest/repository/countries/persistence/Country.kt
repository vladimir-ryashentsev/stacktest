package com.stacktest.repository.countries.persistence

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Country(
    @PrimaryKey val id: Long,
    @ColumnInfo(name = "title") val title: String
)