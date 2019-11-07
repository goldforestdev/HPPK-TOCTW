package com.hppk.toctw.data.source

import com.hppk.toctw.data.model.ChildStampJoin
import com.hppk.toctw.data.model.Stamp
import io.reactivex.Completable
import io.reactivex.Single

interface ChildStampDao {
    fun save(childStampJoin: ChildStampJoin): Completable
    fun getStampsForChild(name: String): Single<List<Stamp>>
}