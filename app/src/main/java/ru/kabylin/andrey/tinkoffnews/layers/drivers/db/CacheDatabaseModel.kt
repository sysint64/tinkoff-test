package ru.kabylin.andrey.tinkoffnews.layers.drivers.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "cache", indices = [(Index("key", unique = true))])
data class CacheDatabaseModel(
    @PrimaryKey
    @ColumnInfo(name = "key")
    val key: String,

    @ColumnInfo(name = "value")
    val value: String,

    @ColumnInfo(name = "ttl")
    val ttl: Long
)
