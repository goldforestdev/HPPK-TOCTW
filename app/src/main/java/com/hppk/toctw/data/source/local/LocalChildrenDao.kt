package com.hppk.toctw.data.source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.hppk.toctw.data.model.Child
import com.hppk.toctw.data.source.ChildrenDao
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface LocalChildrenDao : ChildrenDao {

    @Insert
    override fun save(child: Child): Completable

    @Query("SELECT * FROM child")
    override fun getAll(): Single<List<Child>>

    @Delete
    override fun delete(child: Child): Completable
}