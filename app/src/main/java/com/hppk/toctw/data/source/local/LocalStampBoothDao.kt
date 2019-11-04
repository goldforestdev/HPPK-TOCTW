package com.hppk.toctw.data.source.local

import androidx.room.*
import com.hppk.toctw.data.model.StampBooth
import com.hppk.toctw.data.source.StampBoothDao
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface LocalStampBoothDao : StampBoothDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override fun save(stampBooth: StampBooth): Completable

    @Query("SELECT * FROM stampbooth WHERE boothId=:id LIMIT 1")
    override fun get(id: String): Single<StampBooth>

    @Query("SELECT * FROM stampbooth")
    override fun getAll(): Single<List<StampBooth>>
}