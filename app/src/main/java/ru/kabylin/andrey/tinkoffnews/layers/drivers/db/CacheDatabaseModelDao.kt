package ru.kabylin.andrey.tinkoffnews.layers.drivers.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CacheDatabaseModelDao {
    @Query("SELECT * FROM cache WHERE 'key' = :key")
    fun getValue(key: String): CacheDatabaseModel?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertValue(data: CacheDatabaseModel)

    @Query("DELETE FROM cache")
    fun clear()
}
