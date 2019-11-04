package com.hppk.toctw.data.source

import com.hppk.toctw.data.model.StampBooth
import io.reactivex.Completable
import io.reactivex.Single

interface StampBoothDao {
    fun save(stampBooth: StampBooth): Completable
    fun get(id: String): Single<StampBooth>
    fun getAll() : Single<List<StampBooth>>

}