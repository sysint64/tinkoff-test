package ru.kabylin.andrey.tinkoffnews.layers.drivers.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    version = 1,
    entities = [CacheDatabaseModel::class]
)
abstract class CacheDatabase : RoomDatabase() {
    abstract fun dao(): CacheDatabaseModelDao
}
