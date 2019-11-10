package com.hppk.toctw.data.repository

import android.util.Log
import com.hppk.toctw.data.model.ChildStampJoin
import com.hppk.toctw.data.model.Stamp
import com.hppk.toctw.data.source.impl.FirestoreBoothDao
import com.hppk.toctw.data.source.local.LocalChildStampDao
import com.hppk.toctw.data.source.local.LocalStampDao
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers

class StampRepository(
    private val localStampDao: LocalStampDao,
    private val localChildStampDao: LocalChildStampDao,
    private val remoteBoothDao: FirestoreBoothDao = FirestoreBoothDao()
) {

    fun save(stampBooth: Stamp) = localStampDao.save(stampBooth)

    fun saveChildStampJoin(childStampJoin: ChildStampJoin) = localChildStampDao.save(childStampJoin)

    fun getStamps(childName: String): Single<List<Stamp>> {
        val getAllStamps = getStamps()
        val myStamps = localChildStampDao.getStampsForChild(childName)

        return Single.zip(
            getAllStamps, myStamps, BiFunction { allStamps: List<Stamp>, myStamps: List<Stamp> ->
                allStamps.map { stamp ->
                    stamp.apply {
                        isDone = myStamps.contains(stamp)
                    }
                }.toList()
            })
    }

    fun getStamps(): Single<List<Stamp>> = localStampDao.getAll()
        .filter { it.isNotEmpty() }
        .switchIfEmpty(
            remoteBoothDao.getStampBoothList()
                .map { it.map(::Stamp).toList() }
                .doOnSuccess { stamps ->
                    localStampDao.save(*stamps.toTypedArray())
                        .subscribeOn(Schedulers.io())
                        .subscribe()
                }
        )

}