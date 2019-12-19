package ru.kabylin.andrey.tinkoffnews.layers.drivers.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Maybe

@Dao
interface CacheDatabaseModelDao {
    @Query("SELECT * FROM cache WHERE `key` = :key")
    fun getValue(key: String): Maybe<CacheDatabaseModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertValue(data: CacheDatabaseModel): Completable

    @Query("DELETE FROM cache")
    fun clear(): Completable
}
