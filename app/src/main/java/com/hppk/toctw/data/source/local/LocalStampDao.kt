package com.hppk.toctw.data.source.local

import androidx.room.*
import com.hppk.toctw.data.model.Stamp
import com.hppk.toctw.data.source.StampDao
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface LocalStampDao : StampDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override fun save(vararg stamp: Stamp): Completable

    @Query("SELECT * FROM stamp WHERE id=:id LIMIT 1")
    override fun get(id: String): Single<Stamp>

    @Query("SELECT * FROM stamp")
    override fun getAll(): Single<List<Stamp>>
}