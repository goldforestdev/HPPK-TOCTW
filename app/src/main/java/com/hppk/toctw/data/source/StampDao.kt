package com.hppk.toctw.data.source

import com.hppk.toctw.data.model.Stamp
import io.reactivex.Completable
import io.reactivex.Single

interface StampDao {
    fun save(vararg stamp: Stamp): Completable
    fun get(id: String): Single<Stamp>
    fun getAll() : Single<List<Stamp>>

}