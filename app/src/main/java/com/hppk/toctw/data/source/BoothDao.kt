package com.hppk.toctw.data.source

import com.hppk.toctw.data.model.Booth
import io.reactivex.Completable
import io.reactivex.Single

interface BoothDao {
    fun save(booth: Booth): Completable
    fun get(id: String): Single<Booth>

    fun getDataList() : Single<List<Booth>>
    fun getStampBoothList(): Single<List<Booth>>

}